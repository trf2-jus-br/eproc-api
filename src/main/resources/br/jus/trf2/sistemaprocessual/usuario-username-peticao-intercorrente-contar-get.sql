SELECT
   CAST(ws.dth_inclusao AS DATE) AS data,
   COUNT(ws.id_ws_protocolo_operacao) qtd 
FROM
   ws_protocolo_operacao ws 
   INNER JOIN
      processo p 
      ON (ws.num_processo = p.num_processo) 
WHERE
   ws.id_manifestante = ? 
   AND datediff(CURRENT_DATE(), ws.dth_inclusao) <= ? 
   AND ws.sucesso = 1 
GROUP BY
   CAST(ws.dth_inclusao AS DATE) 
ORDER BY
   CAST(ws.dth_inclusao AS DATE) DESC;