-- Assuntos (Não JEF)
select
	aj.cod_mni_assunto_judicial	id
,	aj.des_assunto				nome
from
	assunto_judicial	aj
inner join
	competencia_assunto_judicial	caj
on
	caj.cod_assunto		=	aj.cod_assunto
and	caj.cod_competencia	=	?
where
	aj.sin_ativo			=	'S'
and	aj.sin_assunto_lancavel	=	'S'
and	aj.tipo_assunto			=	'P'
and	aj.cod_tipo_acao_jef	is	null
union
-- Assuntos (JEF) (sin_jef da tabela competencia_judicial = 'S')
-- Há uma valdação extra para assuntos de JEF
select
	aj.cod_mni_assunto_judicial	id
,	aj.des_assunto				nome
from	assunto_judicial	aj
inner join
	competencia_assunto_judicial	caj
on
	caj.cod_assunto		=	aj.cod_assunto
and	caj.cod_competencia	=	?
inner join
	assunto_tipo_acao_jef	ataj
on
	ataj.id_assunto_judicial	=	aj.id_assunto_judicial
and	ataj.sin_ativo				=	'S'
where
	aj.sin_ativo			=	'S'
and	aj.sin_assunto_lancavel	=	'S'
and	aj.tipo_assunto			=	'P'
and	aj.cod_tipo_acao_jef	is	not null
group by
	aj.cod_mni_assunto_judicial
,	aj.des_assunto
order by
	aj.des_assunto