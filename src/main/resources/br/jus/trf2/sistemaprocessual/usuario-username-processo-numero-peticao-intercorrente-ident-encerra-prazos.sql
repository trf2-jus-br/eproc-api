select
distinct (
   select
      pi.ident_principal 
   from
      pessoa_identificacao pi 
   where
      pi.id_pessoa = pp.id_pessoa 
      and pi.tipo_identificacao = 'CNPJ' 
      and pi.sin_ativo = 'S' 
      and pi.id_pessoa = pi2.id_pessoa) as identEncerraPrazos 
   from
      processo_parte pp,
      pessoa_identificacao pi2 
   WHERE
      pp.id_processo = 
      (
         SELECT
            id_processo 
         FROM
            processo 
         WHERE
            num_processo = ?
      )
      and exists
      (
         select
            1 
         from
            entidade en 
         where
            en.id_pessoa = pp.id_pessoa 
            and en.sin_ativo = 'S'
      )
      and pi2.id_pessoa = pp.id_pessoa 
      and exists
      (
         select
            1 
         from
            usuario_procurador_entidade e 
         where
            e.id_usuario_atuante in
            (
               select
                  u.id_usuario 
               from
                  usuario u 
               where
                  u.id_pessoa = 
                  (
                     select
                        pi.id_pessoa 
                     from
                        pessoa_identificacao pi 
                     where
                        pi.ident_principal = ?
                  )
            )
            and e.sin_ativo = 'S'
      )