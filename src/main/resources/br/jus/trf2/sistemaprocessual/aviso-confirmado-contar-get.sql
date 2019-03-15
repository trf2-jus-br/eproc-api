select
	*
from
	log_abre_fecha_prazo
where
	id_prazo_processo	in	(
		select
			id_prazo_processo
		from
			prazo_processo
		where
			id_processo_parte	in	(selectid_processo_parte
		from	processo_parte
		where
			id_pessoa	=	?)
	)
and	tipo_log			=	'A'		-- Abriu o prazo, isto é, confirmou o aviso/intimação
and	sin_lancou_prazo	=	'S'		-- Parece óbvio, mas preciso confirmar essa informação
