SELECT distinct
   p.id_pessoa codusu,
   s.password hash,
   hf.des_hash_function hash_function,
   hp.des_hash_function hash_persistencia 
from
   senha_mysql s,
   usuario u,
   pessoa p,
   pessoa_identificacao i,
   pessoa_nome n,
   hash_function hf,
   hash_function hp 
WHERE
   u.id_pessoa = p.id_pessoa 
   and p.id_pessoa = s.id_pessoa 
   and p.id_pessoa = i.id_pessoa 
   and p.id_pessoa = n.id_pessoa 
   and p.seq_nome = n.seq_nome_pessoa 
   and hf.cod_hash_function = s.cod_hash_function 
   and hp.cod_hash_function = s.cod_hash_persistencia 
   and p.cod_tipo_pessoa = 'PF' 	-- and	s.dth_desativacao		is	null
   and s.sin_ativo = 'S' 
   AND u.sin_ativo = 'S' 
   and 
   (
      i.tipo_identificacao = 'SIGLA' 
      or i.tipo_identificacao = 'OAB'
   )
   AND 
   (
(FIND_IN_SET (u.cod_tipo_usuario, 
      (
         SELECT
            valor 
         FROM
            infra_parametro 
         WHERE
            nome = 'EPROC_TIPOS_USUARIO_CONSULTA_MNI'
      )
) <> 0) 
      OR u.cod_tipo_usuario IN 
      (
         'A',
         'P',
         'JUS',
         'AT'
      )
   )
   and i.ident_principal = ?;