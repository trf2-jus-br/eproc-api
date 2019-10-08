select
   m.id_minuta id,   
   p.num_processo numero,
   v.conteudo as conteudo,
   m.id_versao_conteudo_ultima versao
from
   minuta m 
   inner join
      versao_conteudo v 
      on v.id_versao_conteudo = m.id_versao_conteudo_ultima 
   inner join
      processo p 
      on p.id_processo = m.id_processo 
   inner join
      v_usuario u 
      on m.id_usuario_assinante_indicado = u.id_usuario 
      and ident_principal = ? 
      and u.cod_tipo_usuario = 'M' 
      and u.sin_ativo = 'S' 
where
   m.id_minuta in (:list)