select
   * 
from
   (
      select distinct
         processo_aviso_comunicacao_envio.dth_inclusao as inclusao,
         processo_evento.dth_evento as data_evento,
         (
            select
               ss.data_sessao 
            from
               processo_evento_sessao ss 
            where
               processo_aviso_comunicacao_envio.id_processo_evento_sessao = ss.id_processo_evento_sessao
         )
         as data_sessao,
         prazo_processo.num_dias_prazo_evento as prazo,
         adddate( processo_evento.dth_evento, interval 10 day) as datalimiteintauto,
         concat( coalesce (( 
         select
            des_evento 
         from
            evento_judicial ej 
         where
            ej.id_evento_judicial = processo_evento.id_evento_judicial), '' ), coalesce( ( 
            select
               concat (des_evento, '-', replace( replace(pe.complemento, '<b>', ''), '</b>', '')) 
            from
               evento_judicial ej, processo_evento_sessao ss, processo_evento pe 
            where
               ej.id_evento_judicial = pe.id_evento_judicial 
               and ss.id_processo_evento = pe.id_processo_evento 
               and processo_aviso_comunicacao_envio.id_processo_evento_sessao = ss.id_processo_evento_sessao ), '') ) as evento,
               (
                  select
                     assunto_judicial.des_assunto 
                  from
                     processo pr 
                     left join
                        assunto_judicial 
                        on(pr.id_assunto_principal = assunto_judicial.id_assunto_judicial) 
                  where
                     pr.id_processo = processo.id_processo 
               )
               as assunto,
               prazo_processo.status_prazo as status,
               processo_aviso_comunicacao_envio.id_processo as idprocesso,
               processo.num_processo as numprocesso,
               --   processo_aviso_comunicacao_envio.num_processo_origem AS numprocessoorigem,
               --    processo_aviso_comunicacao_envio.teor,
               --     processo_aviso_comunicacao_envio.observacao,
               processo_aviso_comunicacao_envio.id_aviso as idaviso,
               --     processo_aviso_comunicacao_envio.id_pessoa AS idpessoa,
               --   mni_tipo_comunicacao.sig_mni_tipo_comunicacao AS sigmnitipocomunicacao,
               mni_tipo_comunicacao.des_mni_tipo_comunicacao as tipo,
               --    processo.cod_competencia AS codcompetencia,
               --    processo.id_classe_judicial AS idclassejudicial,
               --      orgao.des_orgao AS desorgaojuizo,
               orgao.cod_orgao as unidade,
               processo.id_sigilo as idsigilo,
               --  processo.dth_autuacao AS autuacao,
               tabela.codusu 
            from
               (
                  select
                     u.id_usuario codusu 
                  from
                     usuario u 
                     left join
                        pessoa_identificacao pid 
                        on ( u.id_pessoa = pid.id_pessoa 
                        and u.sin_ativo = 'S' ) 
                     left join
                        pessoa pe 
                        on ( pid.id_pessoa = pe.id_pessoa 
                        and pe.cod_tipo_pessoa = 'PF' ) 
                     inner join
                        pessoa_nome pn 
                        on ( pe.id_pessoa = pn.id_pessoa 
                        and pe.seq_nome = pn.seq_nome_pessoa ) 
                     left join
                        pessoa_identificacao icpf 
                        on ( pe.id_pessoa = icpf.id_pessoa 
                        and icpf.tipo_identificacao = 'CPF' ) 
                     left join
                        tipo_usuario tu 
                        on ( u.cod_tipo_usuario = tu.cod_tipo_usuario 
                        and tu.sin_ativo = 'S' ) 
                     left join
                        perfil per 
                        on ( tu.id_perfil_padrao = per.id_perfil 
                        and tu.id_sistema_padrao = per.id_sistema 
                        and per.sin_ativo = 'S' ) 
                     left join
                        orgao o 
                        on ( u.id_orgao_lotacao_usuario = o.id_orgao ) 
                     left join
                        pessoa_contato temail 
                        on ( pe.id_pessoa = temail.id_pessoa 
                        and temail.cod_tipo_contato = 2 
                        and temail.sin_ativo = 'S' 
                        and temail.sin_email_esquecimento_senha = 'S' ) 
                     left join
                        usuario_procurador_entidade upe 
                        on ( u.id_usuario = upe.id_usuario_atuante 
                        and upe.sin_ativo = 'S' ) 
                     left join
                        entidade e 
                        on ( upe.id_pessoa_entidade = e.id_pessoa ) 
                     left join
                        pessoa pessoa_entidade 
                        on ( e.id_pessoa = pessoa_entidade.id_pessoa ) 
                     left join
                        pessoa_nome nome_entidade 
                        on ( pessoa_entidade.id_pessoa = nome_entidade.id_pessoa 
                        and pessoa_entidade.seq_nome = nome_entidade.seq_nome_pessoa ) 
                  where
                     pid.ident_principal = ? 
                     and u.dth_ultimo_acesso = 
                     (
                        select
                           max(usu.dth_ultimo_acesso) 
                        from
                           usuario usu 
                        where
                           usu.id_pessoa = u.id_pessoa 
                        group by
                           usu.id_pessoa 
                     )
                     and exists 
                     (
                        select
                           1 
                        from
                           v_usuario usua 
                        where
                           ident_principal = pid.ident_principal 
                           and sin_ativo = 'S' 
                     )
                     limit 1 
               )
               tabela 
               inner join
                  processo_aviso_comunicacao_envio 
               left join
                  prazo_processo 
                  on (processo_aviso_comunicacao_envio.id_prazo_processo = prazo_processo.id_prazo_processo 
                  and prazo_processo.data_inicial_prazo is null) 
               left join
                  processo_evento 
                  on (prazo_processo.id_processo_evento_abriu = processo_evento.id_processo_evento) 
               left join
                  (
                     processo 
                     inner join
                        orgao 
                        on processo.id_orgao_juizo = orgao.id_orgao 
                  )
                  on processo_aviso_comunicacao_envio.id_processo = processo.id_processo 
               inner join
                  mni_tipo_comunicacao 
                  on processo_aviso_comunicacao_envio.cod_mni_tipo_comunicacao = mni_tipo_comunicacao.cod_mni_tipo_comunicacao 
                  and processo_aviso_comunicacao_envio.id_usuario_destinatario = tabela.codusu 
               inner join
                  processo_parte 
                  on (processo_aviso_comunicacao_envio.id_processo = processo_parte.id_processo) 
               inner join
                  processo_parte_procurador 
                  on (processo_parte.id_processo_parte = processo_parte_procurador.id_processo_parte 
                  and processo_parte_procurador.id_usuario_procurador = processo_aviso_comunicacao_envio.id_usuario_destinatario 
                  and processo_parte_procurador.sin_ativo = 'S') 
            where
               processo_aviso_comunicacao_envio.cod_mni_tipo_comunicacao in 
               (
                  1,
                  2,
                  3,
                  4,
                  5,
                  8 
               )
               and processo_aviso_comunicacao_envio.sin_ativo = 'S' 
            order by
               processo_aviso_comunicacao_envio.id_processo_aviso_comunicacao_envio desc 
   )
   processo 
where
   processo.idsigilo = 0 
   or processo.idsigilo is null 
   or sf_verificaacesso(trim(processo.numprocesso), processo.codusu) = 0 
group by
   date(processo.inclusao),
   processo.idaviso