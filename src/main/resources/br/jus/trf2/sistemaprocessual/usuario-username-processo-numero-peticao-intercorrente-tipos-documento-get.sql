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
               ppp.id_usuario_procurador as procurador_advogado,
               pi.ident_principal as login,
               p.num_processo as processo 
            FROM
               processo_parte_procurador ppp 
               left join
                  processo_parte pp 
                  on (ppp.id_processo_parte = pp.id_processo_parte) 
               left join
                  processo p 
                  on (pp.id_processo = p.id_processo) 
               inner join
                  usuario u 
                  on (ppp.id_usuario_procurador = u.id_usuario) 
               left join
                  pessoa_identificacao pi 
                  on (u.id_pessoa = pi.id_pessoa ) 
            where
               p.num_processo = ? 
               and pi.ident_principal = ? 
            union
            SELECT
               ppp.id_usuario_procurador as procurador_advogado,
               pi_asa.ident_principal as login,
               p.num_processo as processo 
            FROM
               processo_parte_procurador ppp 
               left join
                  processo_parte pp 
                  on (ppp.id_processo_parte = pp.id_processo_parte) 
               left join
                  processo p 
                  on (pp.id_processo = p.id_processo) 
               inner join
                  usuario_analista_advogado uaa 
                  on (ppp.id_usuario_procurador = uaa.id_usuario_advogado 
                  and uaa.sin_ativo = 'S' ) 
               left join
                  usuario u_asa 
                  on (uaa.id_usuario_analista = u_asa.id_usuario) 
               left join
                  pessoa_identificacao pi_asa 
                  on (u_asa.id_pessoa = pi_asa.id_pessoa ) 
            where
               p.num_processo = ? 
               and pi_asa.ident_principal = ? 
            union
           
            SELECT
               ppp.id_usuario_procurador as procurador_advogado,
               pi_aea.ident_principal as login,
               p.num_processo as processo 
            FROM
               processo_parte_procurador ppp 
               left join
                  processo_parte pp 
                  on (ppp.id_processo_parte = pp.id_processo_parte) 
               left join
                  processo p 
                  on (pp.id_processo = p.id_processo) 
               inner join
                  usuario_analista_escritorio uae 
                  on (ppp.id_usuario_procurador = uae.id_usuario_procurador_pessoa_juridica_advogado 
                  and uae.sin_ativo = 'S') 
               left join
                  usuario u_aea 
                  on (uae.id_usuario_procurador_pessoa_juridica_analista = u_aea.id_usuario) 
               left join
                  pessoa_identificacao pi_aea 
                  on (u_aea.id_pessoa = pi_aea.id_pessoa ) 
            WHERE
               p.num_processo = ? 
               and pi_aea.ident_principal = ? 
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
            305,
            310 
         )
         AND tipo_peticao_judicial.sin_peticao_advogado = 'S' 
         AND usu.cod_tipo_usuario = 'A' 
         AND usuario_parte.parte = 1 
      )
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
   tipo_peticao_judicial.des_peticao ASC