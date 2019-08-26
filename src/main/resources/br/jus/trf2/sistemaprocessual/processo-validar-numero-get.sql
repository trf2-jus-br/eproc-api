select
	p.num_processo	numero
,	(
		select
			des_orgao
		from
			orgao	o
		where
			o.id_orgao	=	p.id_orgao_secretaria
	)				unidade
,	(
		select
			l.des_localizador
		from
			processo_localizador	pl
		,	localizador_orgao		lo
		,	localizador				l
		where
			pl.id_processo			=	p.id_processo
		and	pl.id_localizador_orgao	=	lo.id_localizador
		and	lo.id_localizador		=	l.id_localizador
	)				localnaunidade
,	p.id_sigilo		>	0	segredodejustica
,	p.id_sigilo		>=	3	segredodejusticadesistema
,	p.id_sigilo		>=	4	segredodejusticaabsoluto
,	exists(
		select
			*
		from
			processo_evento	pe
		,	evento_judicial	ej
		where
			pe.id_processo			=	p.id_processo
		and	pe.id_evento_judicial	=	ej.id_evento_judicial
		and	ej.id_grupo_evento		=	20
	)				sentenciado
,	status_processo	=	'B'
	baixado
,	status_processo	=	'M' and (com_situacao_processo in (70, 50))
	perdecompetencia
,	(
		select
			group_concat(distinct	numero_cda)
		from
			processo_cda	pc
		where
			pc.id_processo	=	p.id_processo
	)		cdas
,	(
		select
			max(dth_evento)
		from
			processo_evento	pe
		where
			pe.id_processo	=	p.id_processo
	)		dataultimomovimento
from
	processo	p
where
	p.num_processo	=	?