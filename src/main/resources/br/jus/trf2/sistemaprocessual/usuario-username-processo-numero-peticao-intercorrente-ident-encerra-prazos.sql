select
   tabela.identencerraprazos 
from
   (
      select
         pi.ident_principal as identencerraprazos,
         p.num_processo,
         pi2.ident_principal 
      from
         processo_parte pp 
         inner join
            processo p 
            on (pp.id_processo = p.id_processo) 
         inner join
            pessoa_identificacao pi 
            on (pi.id_pessoa = pp.id_pessoa 
            and pi.tipo_identificacao = 'CNPJ' 
            and pi.sin_ativo = 'S') 
         inner join
            entidade en 
            on (en.id_pessoa = pp.id_pessoa 
            and en.sin_ativo = 'S' ) 
         inner join
            usuario_procurador_entidade upe 
            on (upe.id_pessoa_entidade = en.id_pessoa) 
         inner join
            processo_parte_procurador ppp 
            on (ppp.id_processo_parte = pp.id_processo_parte) 
         inner join
            usuario u 
            on (upe.id_usuario_atuante = u.id_usuario 
            and ppp.id_usuario_procurador = u.id_usuario) 
         inner join
            pessoa_identificacao pi2 
            on(u.id_pessoa = pi2.id_pessoa 
            and pi2.sin_ativo = 'S') 
      where
         pi2.ident_principal =? 
      union
      select
         pi3.ident_principal as identencerraprazos,
         p.num_processo,
         pi4.ident_principal 
      from
         processo_parte pp 
         inner join
            processo p 
            on (pp.id_processo = p.id_processo) 
         inner join
            pessoa_identificacao pi3 
            on (pp.id_pessoa = pi3.id_pessoa 
            and pi3.sin_ativo = 'S' 
            and pi3.tipo_identificacao in 
            (
               'CPF',
               'CNPJ' 
            )
) 
         inner join
            processo_parte_procurador ppp2 
            on (ppp2.id_processo_parte = pp.id_processo_parte 
            and ppp2.sin_ativo = 'S') 
         inner join
            usuario u2 
            on (ppp2.id_usuario_procurador = u2.id_usuario) 
         inner join
            pessoa_identificacao pi4 
            on (u2.id_pessoa = pi4.id_pessoa ) 
      where
         pi4.ident_principal = ? 
   )
   tabela 
where
   tabela.num_processo =?