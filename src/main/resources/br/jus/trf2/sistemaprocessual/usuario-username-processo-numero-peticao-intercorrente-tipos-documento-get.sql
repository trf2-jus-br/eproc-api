select
   tipo_documento.cod_tipo_documento as id,
   tipo_documento.des_tipo_documento as nome 
from
   tipo_documento,
   (
      select distinct
         u.id_usuario codusu,
         per.id_perfil perfil,
         pid.ident_principal 
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
   )
   usuario 
where
   tipo_documento.sin_ativo = 'S' 
   and 
   (
      tipo_documento.sin_procurador = 'S' 
      and usuario.perfil in 
      (
         select
            id_perfil 
         from
            perfil 
         where
            descricao like '%ADVOGADO%' 
            or descricao like '%PROCURADOR%' 
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
            descricao like '%POLÍCIA FEDERAL%' 
      )
   )
order by
   tipo_documento.des_tipo_documento