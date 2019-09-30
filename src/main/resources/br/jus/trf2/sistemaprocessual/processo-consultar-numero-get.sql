select
   mag.nome_magistrado magistrado
from
   magistrado mag 
where
   mag.cod_magistrado = 
   (
      select
         jm.cod_magistrado 
      from
         juizo_magistrado jm 
      where
         jm.id_orgao = 
         (
            select
               sso.id_orgao_juizo 
            from
               processo sso 
            where
               sso.id_processo = 
               (
                  select
                     id_processo 
                  from
                     processo 
                  where
                     num_processo = ?
               )
               and jm.dth_final_juizo > current_date()
         )
         and jm.cod_situacao_magistrado_juizo = 1 
         and jm.sin_ativo = 'S' 
         and jm.dth_desativacao is null
   )
   and mag.sin_ativo = 'S';