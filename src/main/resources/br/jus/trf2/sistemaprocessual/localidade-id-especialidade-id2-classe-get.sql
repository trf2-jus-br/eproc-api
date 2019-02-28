select
	classej.id_classe_judicial		id
,	classej.des_classe				nome
,	rcjm.cod_mni_classe_judicial	classecnj
from
	classe_competencia_localidade	ccl
inner join
	localidade_judicial	lj
on
	ccl.id_localidade_judicial	=	lj.id_localidade_judicial
inner join
	competencia_judicial	cj
on
	ccl.cod_competencia	=	cj.cod_competencia
inner join
	classe_judicial	classej
on
	ccl.cod_classe	=	classej.cod_classe
inner join
	relacao_classe_judicial_mni	rcjm
on
	classej.id_classe_judicial	=	rcjm.id_classe_judicial
inner join
	peso_classe_competencia_judicial	pccj
on
	pccj.cod_competencia	=	cj.cod_competencia
and	pccj.id_orgao_juizo		in	(
		select
			o.id_orgao
		from
			orgao	o
		where
			o.id_cod_localidade_judicial	=	lj.id_localidade_judicial
	)
and	pccj.cod_classe			=	classej.cod_classe
where
	ccl.sin_ativo				=	'S'
and	lj.cod_trf_judicial			=	?
and	lj.cod_localidade_judicial	=	?
and	ccl.cod_competencia			=	?
group by
	classej.id_classe_judicial
,	classej.des_classe
,	rcjm.cod_mni_classe_judicial
order by
	classej.des_classe
;