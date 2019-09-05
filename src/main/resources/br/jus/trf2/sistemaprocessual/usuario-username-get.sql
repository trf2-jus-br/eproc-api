SELECT u.id_usuario                 codusu, 
       pn.nome_pessoa               nome, 
       icpf.ident_principal         cpf, 
       temail.contato               email, 
       o.id_orgao                   codunidade, 
       pessoa_entidade.id_pessoa    codentidade, 
       tu.sin_usuario_interno = 'S' usuinterno, 
       tu.sin_usuario_interno = 'N' usuexterno, 
       per.nome                     perfil, 
       pid.ident_principal 
FROM   usuario u 
       LEFT JOIN pessoa_identificacao pid 
              ON ( u.id_pessoa = pid.id_pessoa 
                   AND u.seq_identificacao = pid.seq_identificacao 
                   AND u.sin_ativo = 'S' ) 
       LEFT JOIN pessoa pe 
              ON ( pid.id_pessoa = pe.id_pessoa 
                   AND pe.cod_tipo_pessoa = 'PF' ) 
       INNER JOIN pessoa_nome pn 
               ON ( pe.id_pessoa = pn.id_pessoa 
                    AND pe.seq_nome = pn.seq_nome_pessoa ) 
       LEFT JOIN pessoa_identificacao icpf 
              ON ( pe.id_pessoa = icpf.id_pessoa 
                   AND icpf.tipo_identificacao = 'CPF' ) 
       LEFT JOIN tipo_usuario tu 
              ON ( u.cod_tipo_usuario = tu.cod_tipo_usuario 
                   AND tu.sin_ativo = 'S' ) 
       LEFT JOIN perfil per 
              ON ( tu.id_perfil_padrao = per.id_perfil 
                   AND tu.id_sistema_padrao = per.id_sistema 
                   AND per.sin_ativo = 'S' ) 
       LEFT JOIN orgao o 
              ON ( u.id_orgao_lotacao_usuario = o.id_orgao ) 
       LEFT JOIN pessoa_contato temail 
              ON ( pe.id_pessoa = temail.id_pessoa 
                   AND temail.cod_tipo_contato = 2 
                   AND temail.sin_ativo = 'S' ) 
       LEFT JOIN usuario_procurador_entidade upe 
              ON ( u.id_usuario = upe.id_usuario_atuante ) 
       LEFT JOIN entidade e 
              ON ( upe.id_pessoa_entidade = e.id_pessoa ) 
       LEFT JOIN pessoa pessoa_entidade 
              ON ( e.id_pessoa = pessoa_entidade.id_pessoa ) 
       LEFT JOIN pessoa_nome nome_entidade 
              ON ( pessoa_entidade.id_pessoa = nome_entidade.id_pessoa 
                   AND pessoa_entidade.seq_nome = nome_entidade.seq_nome_pessoa 
                 ) 
WHERE  pid.ident_principal = ?