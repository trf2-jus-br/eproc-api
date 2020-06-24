select
   m.nome_magistrado magistrado 
from
   magistrado m 
   left join
      juizo_magistrado jm 
      on (m.cod_magistrado = jm.cod_magistrado) 
   left join
      processo p 
      on (jm.id_orgao = p.id_orgao_juizo) 
   left join
      situacao_magistrado_juizo smj 
      on (jm.cod_situacao_magistrado_juizo = smj.cod_situacao_magistrado_juizo) 
where
   p.num_processo = ? 
   and current_date() between dth_inicio_juizo and jm.dth_final_juizo 
   and smj.des_situacao_magistrado_juizo = 'ATUANTE' 
   and jm.sin_ativo = 'S' 
   and jm.dth_desativacao is null 
   and m.sin_ativo = 'S';