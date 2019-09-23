select
   e.id_pessoa id,
   pn.nome_pessoa nome,
   pi.ident_principal documento,
   pi.tipo_identificacao tipodedocumento 
from
   pessoa_nome pn 
   inner join
      pessoa_identificacao pi 
      on (pi.id_pessoa = pn.id_pessoa) 
   inner join
      entidade e 
      on (e.id_pessoa = pn.id_pessoa 
      and e.seq_nome_pessoa = pn.seq_nome_pessoa) 
where
   pi.sin_ativo = 'S' 
   and pn.sin_ativo = 'S' 
   and pi.tipo_identificacao = 'CNPJ' 
   and e.sin_ativo = 'S' 
   and pi.ident_principal is not null 
   and e.id_tipo_entidade <> 11 
order by
   pn.nome_pessoa