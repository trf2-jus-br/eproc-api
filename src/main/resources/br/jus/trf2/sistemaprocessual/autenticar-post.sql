select
	p.id_pessoa			codusu
,	s.password			hash
,   hf.des_hash_function	hash_function
,   hp.des_hash_function	hash_persistencia
from
	senha_mysql				s
,	pessoa					p
,	pessoa_identificacao	i
,	pessoa_nome				n
,	hash_function			hf
,	hash_function			hp
where
	p.id_pessoa				=	s.id_pessoa
and	p.id_pessoa				=	i.id_pessoa
and	p.id_pessoa				=	n.id_pessoa
and	p.seq_nome				=	n.seq_nome_pessoa
and hf.cod_hash_function	=	s.cod_hash_function
and hp.cod_hash_function	=	s.cod_hash_persistencia
and	p.cod_tipo_pessoa		=	'PF'
-- and	s.dth_desativacao		is	null
and	s.sin_ativo				=	'S'
and	(i.tipo_identificacao	= 'SIGLA' or i.tipo_identificacao	= 'OAB')
and	i.ident_principal		=	?;