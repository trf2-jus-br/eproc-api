select
   tipo_peticao_judicial.id_tipo_peticao_judicial as id,
   tipo_peticao_judicial.des_peticao as nome 
from
   tipo_peticao_judicial,
   (
      select distinct
         tabela.id_usuario,
         tabela.cod_tipo_usuario,
         (
            if ((tabela2.id_usuario_procurador is not null) 
            and 
            (
               tabela.id_usuario = tabela2.id_usuario_procurador 
            )
, true, false) 
         )
         as parte_processo 
      from
         (
            select
               usuario.id_usuario,
               usuario.cod_tipo_usuario,
               usuario.id_pessoa,
               usuario.dth_ultimo_acesso,
               perfil.id_perfil,
               s.id_sigilo,
               pessoa_identificacao.ident_principal 
            from
               usuario 
               left join
                  tipo_usuario 
                  on (usuario.cod_tipo_usuario = tipo_usuario.cod_tipo_usuario) 
               left join
                  perfil 
                  on (tipo_usuario.id_perfil_padrao = perfil.id_perfil 
                  and tipo_usuario.id_sistema_padrao = perfil.id_sistema) 
               left join
                  sigilo_perfil sp 
                  on (perfil.id_perfil = sp.id_perfil) 
               left join
                  sigilo s 
                  on (sp.id_sigilo = s.id_sigilo) 
               left join
                  pessoa_identificacao 
                  on (usuario.id_pessoa = pessoa_identificacao.id_pessoa) 
            where
               pessoa_identificacao.ident_principal = ? 
         )
         tabela 
         left join
            pessoa_identificacao 
            on (tabela.ident_principal = pessoa_identificacao.ident_principal) 
         left join
            usuario 
            on (pessoa_identificacao.id_pessoa = usuario.id_pessoa 
            and pessoa_identificacao.seq_identificacao = usuario.seq_identificacao) 
         left join
            (
               select distinct
                  ppp.id_usuario_procurador,
                  p.num_processo,
                  pp.id_sigilo 
               from
                  processo_parte_procurador ppp 
                  left join
                     processo_parte pp 
                     on (ppp.id_processo_parte = pp.id_processo_parte) 
                  left join
                     processo p 
                     on (pp.id_processo = p.id_processo) 
                  left join
                     usuario u 
                     on (ppp.id_usuario_procurador = u.id_usuario) 
                  left join
                     pessoa_identificacao pi 
                     on (u.id_pessoa = pi.id_pessoa ) 
                  left join
                     usuario_analista_advogado uaa 
                     on (ppp.id_usuario_procurador = uaa.id_usuario_advogado 
                     and uaa.sin_ativo = 'S' ) 
                  left join
                     usuario u_asa 
                     on (uaa.id_usuario_analista = u_asa.id_usuario) 
                  left join
                     pessoa_identificacao pi_asa 
                     on (u_asa.id_pessoa = pi_asa.id_pessoa ) 
                  left join
                     usuario_analista_escritorio uae 
                     on (ppp.id_usuario_procurador = uae.id_usuario_procurador_pessoa_juridica_advogado 
                     and uae.sin_ativo = 'S') 
                  left join
                     usuario u_aea 
                     on (uae.id_usuario_procurador_pessoa_juridica_analista = u_aea.id_usuario) 
                  left join
                     pessoa_identificacao pi_aea 
                     on (u_aea.id_pessoa = pi_aea.id_pessoa ) 
               where
                  p.num_processo = ? 
            )
            tabela2 
            on (usuario.id_usuario = tabela2.id_usuario_procurador) 
      where
         (
            if ((tabela2.id_usuario_procurador is not null) 
            and 
            (
               tabela.id_usuario = tabela2.id_usuario_procurador 
            )
, true, false) 
         )
         = true 
         or tabela.dth_ultimo_acesso = 
         (
            select
               max(usu.dth_ultimo_acesso) 
            from
               usuario usu 
            where
               usu.id_pessoa = tabela.id_pessoa 
         )
   )
   as usu 
where
   tipo_peticao_judicial.sin_ativo = 'S' 
   and tipo_peticao_judicial.sin_lancavel_externo = 'S' 
   and tipo_peticao_judicial.sin_ativo = 'S' 
   and 
   (
( tipo_peticao_judicial.sin_peticao_advogado = 'S' 
      and tipo_peticao_judicial.id_tipo_peticao_judicial in 
      (
         52,
         105 
      )
      and usu.cod_tipo_usuario = 'A' 
      and usu.parte_processo = 0 ) 
      or 
      (
         tipo_peticao_judicial.id_tipo_peticao_judicial in 
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
         and tipo_peticao_judicial.sin_peticao_advogado = 'S' 
         and usu.cod_tipo_usuario = 'A' 
         and usu.parte_processo = 1 
      )
      or 
      (
         tipo_peticao_judicial.id_tipo_peticao_judicial in 
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
         and tipo_peticao_judicial.sin_peticao_procurador = 'S' 
         and usu.cod_tipo_usuario = 'P' 
         and usu.parte_processo = 1 
      )
   )
group by
   id,
   nome 
order by
   tipo_peticao_judicial.des_peticao asc