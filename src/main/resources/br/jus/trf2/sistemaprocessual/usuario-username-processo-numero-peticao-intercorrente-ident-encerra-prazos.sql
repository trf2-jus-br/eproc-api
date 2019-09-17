select distinct
( 
   select
      pi.ident_principal 
   from
      pessoa_identificacao pi 
   where
      pi.id_pessoa = pp.id_pessoa 
      and pi.tipo_identificacao = 'CNPJ' 
      and pi.sin_ativo = 'S' 
      and pi.id_pessoa = pi2.id_pessoa) as identencerraprazos 
   from
      processo_parte pp,
      pessoa_identificacao pi2 
   where
      pp.id_processo = 
      (
         select
            id_processo 
         from
            processo 
         where
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
   union
   select
      pii.ident_principal as identencerraprazos 
   from
      pessoa_identificacao pii 
   where
      pii.id_pessoa = 
      (
         select
            pa.id_pessoa 
         from
            processo_parte pa 
         where
            pa.id_processo_parte = 
            (
               select
                  ppp.id_processo_parte 
               from
                  processo_parte_procurador ppp 
                  left join
                     processo_parte ppe 
                     on (ppp.id_processo_parte = ppe.id_processo_parte) 
                  left join
                     processo p 
                     on (ppe.id_processo = p.id_processo) 
                  inner join
                     usuario u 
                     on (ppp.id_usuario_procurador = u.id_usuario) 
                  left join
                     pessoa_identificacao pi 
                     on (u.id_pessoa = pi.id_pessoa ) 
               where
                  p.id_processo = 
                  (
                     select
                        h.id_processo 
                     from
                        processo h 
                     where
                        h.num_processo = ?
                  )
                  and pi.ident_principal = ?
            )
      )
      and pii.sin_ativo = 'S' 
      and pii.tipo_identificacao = 'CPF';