select
   pcda.numero_cda,
   pcda.num_processo_adm,
   (
      select
         des_cda_status 
      from
         cda_status cda 
      where
         cda.id_cda_status = pcda.id_cda_status
   )
   as Status,
   (
      select
         cst.des_cda_grupo_status 
      from
         cda_grupo_status cst,
         cda_status st 
      where
         st.id_cda_grupo_status = cst.id_cda_grupo_status 
         and st.id_cda_status = pcda.id_cda_status
   )
   as Grupo_Status,
   pcda.codigo_tributo_fiscal,
   pcda.descicao_tributo_fiscal as Tributo,
   pcda.valor_cda,
   pcda.dth_origem as Data_Origem,
   pcda.valor_ufir,
   pcda.dth_inclusao 
from
   processo_cda pcda 
where
   pcda.id_processo = 
   (
      select
         id_processo 
      from
         processo pss 
      where
         pss.num_processo = ?
   )
   and pcda.sin_ativo = 'S' 
order by
   7 desc;