SELECT
   ws.id_manifestante,
   ws.id_ws_protocolo_operacao protocolo,
   ws.dth_inclusao,
   p.num_processo,
   p.id_classe_judicial,
   o.cod_orgao,
   o.sig_orgao,
   o.des_orgao 
from
   ws_protocolo_operacao ws 
   INNER JOIN
      processo p 
      on (ws.num_processo = p.num_processo) 
   LEFT JOIN
      orgao o 
      ON (p.id_orgao_secretaria = o.id_orgao) 
WHERE
   ws.id_manifestante = ? 
   and cast(ws.dth_inclusao as DATE) =?