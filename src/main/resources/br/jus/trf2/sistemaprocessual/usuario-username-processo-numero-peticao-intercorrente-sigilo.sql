select
   tabela.id_sigilo as sigilo,
   sum( if ((tabela2.id_usuario_procurador is not null) 
   and 
   (
      tabela.id_usuario = tabela2.id_usuario_procurador 
   )
, true, false)) > 0 parte 
from
   (
      select
         usuario.id_usuario,
         usuario.cod_tipo_usuario,
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
      on (usuario.id_usuario = tabela2.id_usuario_procurador);