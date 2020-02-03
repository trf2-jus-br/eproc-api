select distinct
   tipo_documento.cod_tipo_documento as id,
   tipo_documento.des_tipo_documento as nome 
from
   tipo_documento,
   (
      select
         tipo_peticao_judicial.id_tipo_peticao_judicial as id,
         tipo_peticao_judicial.des_peticao as nome,
         usu.id_perfil as perfil,
         usu.parte_processo 
      from
         tipo_peticao_judicial,
         (
            select distinct
               tabela.id_usuario,
               tabela.cod_tipo_usuario,
               tabela.id_perfil,
               tabela2.num_processo,
               (
                  if ((tabela2.id_usuario_procurador is not null) 
                  and 
                  (
                     tabela.id_usuario = tabela2.id_usuario_procurador 
                  )
, true, false) 
               )
               as parte_processo 
            from
               (
                  select
                     usuario.id_usuario,
                     usuario.cod_tipo_usuario,
                     usuario.id_pessoa,
                     usuario.dth_ultimo_acesso,
                     perfil.id_perfil,
                     s.id_sigilo,
                     pessoa_identificacao.ident_principal 
                  from
                     usuario 
                     left join
                        tipo_usuario 
                        on (usuario.cod_tipo_usuario = tipo_usuario.cod_tipo_usuario) 
                     left join
                        perfil 
                        on (tipo_usuario.id_perfil_padrao = perfil.id_perfil 
                        and tipo_usuario.id_sistema_padrao = perfil.id_sistema) 
                     left join
                        sigilo_perfil sp 
                        on (perfil.id_perfil = sp.id_perfil) 
                     left join
                        sigilo s 
                        on (sp.id_sigilo = s.id_sigilo) 
                     left join
                        pessoa_identificacao 
                        on (usuario.id_pessoa = pessoa_identificacao.id_pessoa) 
                  where
                     pessoa_identificacao.ident_principal = ? 
               )
               tabela 
               left join
                  pessoa_identificacao 
                  on (tabela.ident_principal = pessoa_identificacao.ident_principal) 
               left join
                  usuario 
                  on (pessoa_identificacao.id_pessoa = usuario.id_pessoa 
                  and pessoa_identificacao.seq_identificacao = usuario.seq_identificacao) 
               left join
                  (
                     select distinct
                        ppp.id_usuario_procurador,
                        p.num_processo,
                        pp.id_sigilo 
                     from
                        processo_parte_procurador ppp 
                        left join
                           processo_parte pp 
                           on (ppp.id_processo_parte = pp.id_processo_parte) 
                        left join
                           processo p 
                           on (pp.id_processo = p.id_processo) 
                        left join
                           usuario u 
                           on (ppp.id_usuario_procurador = u.id_usuario) 
                        left join
                           pessoa_identificacao pi 
                           on (u.id_pessoa = pi.id_pessoa ) 
                        left join
                           usuario_analista_advogado uaa 
                           on (ppp.id_usuario_procurador = uaa.id_usuario_advogado 
                           and uaa.sin_ativo = 'S' ) 
                        left join
                           usuario u_asa 
                           on (uaa.id_usuario_analista = u_asa.id_usuario) 
                        left join
                           pessoa_identificacao pi_asa 
                           on (u_asa.id_pessoa = pi_asa.id_pessoa ) 
                        left join
                           usuario_analista_escritorio uae 
                           on (ppp.id_usuario_procurador = uae.id_usuario_procurador_pessoa_juridica_advogado 
                           and uae.sin_ativo = 'S') 
                        left join
                           usuario u_aea 
                           on (uae.id_usuario_procurador_pessoa_juridica_analista = u_aea.id_usuario) 
                        left join
                           pessoa_identificacao pi_aea 
                           on (u_aea.id_pessoa = pi_aea.id_pessoa ) 
                     where
                        p.num_processo = ? 
                  )
                  tabela2 
                  on (usuario.id_usuario = tabela2.id_usuario_procurador ) 
            where
               (
                  if ((tabela2.id_usuario_procurador is not null) 
                  and 
                  (
                     tabela.id_usuario = tabela2.id_usuario_procurador 
                  )
, true, false) 
               )
               = true 
               or tabela.dth_ultimo_acesso = 
               (
                  select
                     max(usu.dth_ultimo_acesso) 
                  from
                     usuario usu 
                  where
                     usu.id_pessoa = tabela.id_pessoa 
               )
         )
         as usu 
      where
         tipo_peticao_judicial.sin_ativo = 'S' 
         and tipo_peticao_judicial.sin_lancavel_externo = 'S' 
         and tipo_peticao_judicial.sin_ativo = 'S' 
         and 
         (
( tipo_peticao_judicial.sin_peticao_advogado = 'S' 
            and tipo_peticao_judicial.id_tipo_peticao_judicial in 
            (
               52,
               105 
            )
            and usu.cod_tipo_usuario in 
            (
               select
                  cod_tipo_usuario 
               from
                  tipo_usuario 
               where
                  id_perfil_padrao in 
                  (
                     select
                        id_perfil 
                     from
                        perfil 
                     where
                        nome like '%ADVOGADO%' 
                        and sin_ativo = 'S' 
                  )
                  OR cod_tipo_usuario = 'CEF'
            )
            and usu.parte_processo = 0 ) 
            or 
            (
               tipo_peticao_judicial.id_tipo_peticao_judicial in 
               (
                  select
                     tpj.id_tipo_peticao_judicial 
                  from
                     classe_tipo_peticao ctp 
                     inner join
                        tipo_peticao_judicial tpj 
                        on (ctp.id_tipo_peticao_judicial = tpj.id_tipo_peticao_judicial) 
                     inner join
                        classe_judicial cj 
                        on (ctp.id_classe_judicial = cj.id_classe_judicial) 
                     inner join
                        processo p 
                        on (cj.id_classe_judicial = p.id_classe_judicial) 
                  where
                     tpj.sin_ativo = 'S' 
                     and ctp.sin_ativo = 'S' 
                     and p.num_processo = usu.num_processo 
                  order by
                     tpj.id_tipo_peticao_judicial 
               )
               and tipo_peticao_judicial.sin_peticao_advogado = 'S' 
               and usu.cod_tipo_usuario in 
               (
                  select
                     cod_tipo_usuario 
                  from
                     tipo_usuario 
                  where
                     id_perfil_padrao in 
                     (
                        select
                           id_perfil 
                        from
                           perfil 
                        where
                           nome like '%ADVOGADO%' 
                           and sin_ativo = 'S' 
                     )
                   
               )
               and usu.parte_processo = 1 
            )
            or 
            (
               tipo_peticao_judicial.sin_peticao_advogado = 'S' 
               and tipo_peticao_judicial.id_tipo_peticao_judicial in 
               (
                  52,
                  105 
               )
               and usu.cod_tipo_usuario in 
               (
                  select
                     cod_tipo_usuario 
                  from
                     tipo_usuario 
                  where
                    ( sin_perfil_mpf = 'S' 
                     and sin_ativo = 'S')
					or cod_tipo_usuario = 'CEF' 
               )
               and usu.parte_processo = 0 
            )
            or 
            (
               tipo_peticao_judicial.id_tipo_peticao_judicial in 
               (
                  select
                     tpj.id_tipo_peticao_judicial 
                  from
                     classe_tipo_peticao ctp 
                     inner join
                        tipo_peticao_judicial tpj 
                        on (ctp.id_tipo_peticao_judicial = tpj.id_tipo_peticao_judicial) 
                     inner join
                        classe_judicial cj 
                        on (ctp.id_classe_judicial = cj.id_classe_judicial) 
                     inner join
                        processo p 
                        on (cj.id_classe_judicial = p.id_classe_judicial) 
                  where
                     tpj.sin_ativo = 'S' 
                     and ctp.sin_ativo = 'S' 
                     and p.num_processo = usu.num_processo 
                  order by
                     tpj.id_tipo_peticao_judicial 
               )
               and tipo_peticao_judicial.sin_peticao_procurador = 'S' 
               and usu.cod_tipo_usuario in 
               (
                  select
                     cod_tipo_usuario 
                  from
                     tipo_usuario 
                  where
                     (sin_perfil_mpf = 'S' 
                     and sin_ativo = 'S')
						
               )
               and usu.parte_processo = 1 
            )
         )
      group by
         id,
         nome 
   )
   usuario_tipo_peticao 
where
   tipo_documento.sin_ativo = 'S' 
   and 
   (
( tipo_documento.sin_procurador = 'S' 
      and usuario_tipo_peticao.perfil in 
      (
         select
            id_perfil 
         from
            perfil 
         where
            id_perfil in 
            (
               select
                  id_perfil_padrao 
               from
                  tipo_usuario 
               where
                  sin_perfil_mpf = 'S' 
                  and sin_ativo = 'S'
					 
            )
            or nome like'ADVOGADO%' 
          
            and sin_ativo = 'S' 
      )
) 
      or 
      (
         tipo_documento.sin_pf = 'S' 
         and perfil in 
         (
            select
               id_perfil 
            from
               perfil 
            where
               id_perfil in 
               (
                  select
                     id_perfil_padrao 
                  from
                     tipo_usuario 
                  where
                    ( sin_perfil_pf = 'S' 
                     and sin_ativo = 'S')
						 
               )
               or id_perfil=81 -- Perfil de usuario externo da CEF no TRF2
         )
      )
   )
   and 
   (
      usuario_tipo_peticao.parte_processo = true 
      or 
      (
         usuario_tipo_peticao.parte_processo = false 
         and tipo_documento.cod_tipo_documento not in 
         (
            204
         )
      )
   )
order by
   tipo_documento.des_tipo_documento