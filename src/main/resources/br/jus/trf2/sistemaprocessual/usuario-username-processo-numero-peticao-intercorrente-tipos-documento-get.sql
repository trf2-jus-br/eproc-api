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
         usu.id_usuario in 
         (
            SELECT
               id_usuario 
            FROM
               usuario,
               pessoa_identificacao 
            WHERE
               usuario.id_pessoa = pessoa_identificacao.id_pessoa 
               and pessoa_identificacao.ident_principal = ?
         )
   )
   as usu,
   (
      SELECT
         COUNT(*) > 0 parte 
      FROM
         (
            SELECT
               u.id_usuario AS codusuario 
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
               u.id_usuario in 
               (
                  SELECT
                     id_usuario 
                  FROM
                     usuario,
                     pessoa_identificacao 
                  WHERE
                     usuario.id_pessoa = pessoa_identificacao.id_pessoa 
                     and pessoa_identificacao.ident_principal = ?
               )
               AND p.num_processo = ?
            UNION
            select
               tabela.id_usuario_procurador as codusuario 
            from
               (
                  SELECT
                     id_usuario_procurador,
                     (
                        SELECT
                           pn.nome_pessoa 
                        FROM
                           usuario u,
                           pessoa_nome pn 
                        WHERE
                           pn.id_pessoa = u.id_pessoa 
                           and u.id_usuario = id_usuario_procurador
                     )
                     as Procurador,
                     (
                        SELECT
                           pn.id_pessoa 
                        FROM
                           usuario u,
                           pessoa_nome pn 
                        WHERE
                           pn.id_pessoa = u.id_pessoa 
                           and u.id_usuario = id_usuario_procurador
                     )
                     as Id_pessoa 
                  FROM
                     processo_parte_procurador 
                  WHERE
                     id_processo_parte IN
                     (
                        SELECT
                           id_processo_parte 
                        from
                           processo_parte pp 
                           inner join
                              entidade e 
                              ON (pp.id_pessoa = e.id_pessoa ) 
                        WHERE
                           id_processo = 
                           (
                              SELECT
                                 id_processo 
                              FROM
                                 processo 
                              WHERE
                                 num_processo = ?
                           )
                     )
               )
               as tabela 
            where
               Id_pessoa = 
               (
                  SELECT distinct
                     u.id_pessoa 
                  FROM
                     usuario u,
                     pessoa_nome pnn,
                     pessoa_identificacao pi 
                  WHERE
                     pnn.id_pessoa = u.id_pessoa 
                     and pi.ident_principal = ?
                     and pi.id_pessoa = pnn.id_pessoa 
                     and pi.sin_ativo = 'S'
               )
         )
         AS tabela 
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
      AND usuario_parte.parte = 0 ) 
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
         AND usuario_parte.parte = 1 
      )
   )
GROUP BY
   id,
   nome 
ORDER BY
   tipo_peticao_judicial.des_peticao ASC;