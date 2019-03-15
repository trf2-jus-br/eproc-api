SELECT 
	date(pe.dth_evento) as yyyymmdd, 
	count(*) as quantidadeDoUsuarioPorConfirmacao, 
	null as quantidadeDoUsuarioPorOmissao, 
	null as quantidadeDoGrupoPorConfirmacao, 
	null as quantidadeDoGrupoPorOmissao
FROM
	v_usuario u
	inner join processo_parte_procurador ppp on u.id_usuario = ppp.id_usuario_procurador
	inner join prazo_processo_parte_procurador pppp on pppp.id_processo_parte_procurador = ppp.id_processo_parte_procurador 
	inner join prazo_processo pp on pp.id_prazo_processo = pppp.id_prazo_processo
	inner join processo_evento pe  on pe.id_processo_evento = pp.id_processo_evento_abriu
where u.id_pessoa = ?
group by yyyymmdd;
   