select
	p.id_pessoa	codusu
,	s.password	hash
from
	senha_mysql				s
,	pessoa					p
,	pessoa_identificacao	i
,	pessoa_nome				n
where
	p.id_pessoa				=	s.id_pessoa
and	p.id_pessoa				=	i.id_pessoa
and	p.id_pessoa				=	n.id_pessoa
and	p.seq_nome				=	n.seq_nome_pessoa
and	p.cod_tipo_pessoa		=	'PF'
-- and	s.dth_desativacao		is	null
and	s.sin_ativo				=	'S'
and	(i.tipo_identificacao	= 'SIGLA' or i.tipo_identificacao	= 'OAB')
and	i.ident_principal		=	?