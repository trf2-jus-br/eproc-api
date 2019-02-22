select p.id_pessoa id, pn.nome_pessoa nome, pi.ident_principal documento, pi.tipo_identificacao tipodedocumento
from pessoa_identificacao pi 
inner join pessoa p 
on pi.id_pessoa = p.id_pessoa
and pi.tipo_identificacao = 'CNPJ'
and p.cod_tipo_pessoa = 'PJ'
and pi.ident_principal = ? 
inner join pessoa_nome pn
on p.id_pessoa = pn.id_pessoa
and pn.sin_ativo = 'S';