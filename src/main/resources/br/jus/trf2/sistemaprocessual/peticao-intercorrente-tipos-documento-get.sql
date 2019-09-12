--SELECT cod_tipo_documento id, des_tipo_documento nome 
--FROM tipo_documento 
--where sin_ativo = 'S' and (sin_procurador = 'S' or sin_mpf = 'S') 
--order by des_tipo_documento = 'PETIÇÃO' desc, des_tipo_documento;

SET @cod_usuario =? ;
SET  @num_processo =? ;
--valores para advogado não parte   
-- SET  @cod_usuario = '511527185120153521027281539492';
--  SET  @num_processo = '50038094720194025110';
SELECT
   tipo_peticao_judicial.id_tipo_peticao_judicial AS id,
   tipo_peticao_judicial.des_peticao AS nome

FROM
   tipo_peticao_judicial,
   (
      SELECT
         usu.id_usuario,
         usu.cod_tipo_usuario 
      FROM
         usuario usu 
      WHERE
         usu.id_usuario = @cod_usuario
   )
   as usu,
   (
      SELECT
         COUNT(*) > 0 parte 
      FROM
         (
            SELECT
               u.id_usuario,
               p.num_processo 
            FROM
               usuario u 
               LEFT JOIN
                  tipo_usuario tu 
                  ON (u.cod_tipo_usuario = tu.cod_tipo_usuario) 
               LEFT JOIN
                  usuario_analista_advogado uaa 
                  ON(u.cod_tipo_usuario = 'ASA' 
                  AND u.id_usuario = uaa.id_usuario_analista 
                  AND uaa.sin_ativo = 'S') 
               LEFT JOIN
                  usuario_analista_escritorio uae 
                  ON (u.cod_tipo_usuario = 'AEA' 
                  AND u.id_usuario = uae.id_usuario_procurador_pessoa_juridica_analista 
                  AND uae.sin_ativo = 'S') 
               LEFT JOIN
                  processo_parte_procurador ppp 
                  ON (u.id_usuario = ppp.id_usuario_procurador 
                  OR uaa.id_usuario_advogado 
                  OR uae.id_usuario_procurador_pessoa_juridica_advogado) 
               LEFT JOIN
                  processo p 
                  ON (ppp.id_processo_parte = p.id_processo) 
            WHERE
               u.id_usuario = @cod_usuario 
               AND p.num_processo = @num_processo 
         )
         tabela
   )
   AS usuario_parte 
WHERE
   tipo_peticao_judicial.sin_ativo = 'S' 
   AND tipo_peticao_judicial.des_peticao LIKE '%%' 
   AND tipo_peticao_judicial.sin_lancavel_externo = 'S' 
   AND tipo_peticao_judicial.sin_ativo = 'S' 
   AND 
   (
( tipo_peticao_judicial.sin_peticao_advogado = 'S' 
      AND tipo_peticao_judicial.id_tipo_peticao_judicial IN 
      (
         52,
         105
      )
      AND usu.cod_tipo_usuario = 'A' 
      AND usuario_parte.parte = false ) # AdvogadoNãoParte 
      OR  
      (
         tipo_peticao_judicial.id_tipo_peticao_judicial IN 
         (
            31,
            32,
            180,
            102,
            35,
            36,
            38,
            73,
            170,
            166,
            167,
            177,
            181,
            168,
            169,
            104,
            40,
            41,
            42,
            92,
            43,
            86,
            44,
            101,
            48,
            49,
            51,
            76,
            174,
            165,
            179,
            52,
            72,
            79,
            107,
            108,
            88,
            81,
            82,
            105,
            53,
            95,
            80,
            55,
            61,
            63,
            172,
            171,
            106,
            65,
            178,
            66,
            164,
            163,
            182,
            201,
            207,
            209,
            210,
            211,
            305 
         )
         AND tipo_peticao_judicial.sin_peticao_procurador = 'S' 
         AND usu.cod_tipo_usuario = 'P' 
         AND usuario_parte.parte = true 
      )       # procuradorParte 
   )
ORDER BY
   tipo_peticao_judicial.des_peticao ASC
