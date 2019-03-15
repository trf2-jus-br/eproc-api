select
	n.id_pessoa				codusu
,	n.nome_pessoa			nome
,	icpf.ident_principal	cpf
,	temail.contato			email
from
	pessoa	p
join
	pessoa_identificacao	i
on
	(
		p.id_pessoa	=	i.id_pessoa
	)
join
	pessoa_nome	n
on
	(
		p.id_pessoa	=	n.id_pessoa
	)
left join
	pessoa_identificacao	icpf
on
	(
		p.id_pessoa				=	icpf.id_pessoa
	and	icpf.tipo_identificacao	=	'CPF'
	)
left outer join
	pessoa_contato	temail
on
	(
		p.id_pessoa				=	temail.id_pessoa
	and	temail.cod_tipo_contato	=	2
	and	temail.sin_ativo		=	'N'
	)
where
	p.seq_nome				=	n.seq_nome_pessoa
and	p.cod_tipo_pessoa		=	'PF'
and	i.tipo_identificacao	=	'SIGLA'
and	i.ident_principal		=	?
