SELECT
   cast(pe.dth_evento as date) as data,
   count(pe.id_processo_evento) qtd 
FROM
   processo_evento pe 
   LEFT JOIN
      processo p 
      ON (pe.id_processo = p.id_processo ) 
WHERE
   pe.id_usuario = ? 
   and pe.id_tipo_peticao_judicial is not null 
   and datediff(CURRENT_DATE(), pe.dth_evento) <= ? 
group by
   cast(pe.dth_evento as date) 
order by
   cast(pe.dth_evento as date) desc