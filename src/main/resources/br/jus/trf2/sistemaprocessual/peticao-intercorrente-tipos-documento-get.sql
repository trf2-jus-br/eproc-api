SELECT cod_tipo_documento id, des_tipo_documento nome 
FROM tipo_documento 
where sin_ativo = 'S' and (sin_procurador = 'S' or sin_mpf = 'S') 
order by des_tipo_documento = 'PETIÇÃO' desc, des_tipo_documento;