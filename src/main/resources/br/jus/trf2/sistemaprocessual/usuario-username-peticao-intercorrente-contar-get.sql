select 
cast(ws.dth_inclusao as date) as data_peticao,
   count(ws.id_ws_protocolo_operacao) qtd_peticoes 
from  ws_protocolo_operacao ws  	
inner join processo p
	on (ws.num_processo=p.num_processo)
WHERE
   ws.id_manifestante = ? 
   and datediff(CURRENT_DATE(), ws.dth_inclusao) <= ? 
   and ws.sucesso =1 
  
group by
   cast(ws.dth_inclusao as date) 
order by
   cast(ws.dth_inclusao as date) desc;