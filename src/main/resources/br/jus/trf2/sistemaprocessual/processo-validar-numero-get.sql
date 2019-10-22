select
   ip.valor as instancia,
   p.num_processo numero,
   (
      select
         des_orgao 
      from
         orgao o 
      where
         o.id_orgao = p.id_orgao_secretaria 
   )
   unidade,
   (
      select
         group_concat(l.des_localizador) 
      from
         processo_localizador pl,
         localizador_orgao lo,
         localizador l 
      where
         pl.id_processo = p.id_processo 
         and pl.id_localizador_orgao = lo.id_localizador 
         and lo.id_localizador = l.id_localizador 
      group by
         p.id_processo 
   )
   localnaunidade,
   p.id_sigilo > 0 segredodejustica,
   p.id_sigilo >= 3 segredodejusticadesistema,
   p.id_sigilo >= 4 segredodejusticaabsoluto,
   exists 
   (
      select
         * 
      from
         processo_evento pe,
         evento_judicial ej 
      where
         pe.id_processo = p.id_processo 
         and pe.id_evento_judicial = ej.id_evento_judicial 
         and ej.id_grupo_evento = 20 
   )
   sentenciado,
   status_processo = 'B' baixado,
   (
      status_processo = 'M' 
      and 
      (
         com_situacao_processo in 
         (
            75,
            70,
            50 
         )
      )
   )
   or 
   (
      ip.valor = 2 
      and status_processo = 'B' 
      and p.id_classe_judicial not in 
      (
         select
            id_classe_judicial 
         from
            classe_judicial 
         where
            id_classe_judicial in 
            (
               009666,
               009388,
               009445,
               009333 
            )
      )
   )
   perdecompetencia,
   (
      select
         group_concat(distinct numero_cda) 
      from
         processo_cda pc 
      where
         pc.id_processo = p.id_processo 
   )
   cdas,
   (
      select
         max(dth_evento) 
      from
         processo_evento pe 
      where
         pe.id_processo = p.id_processo 
   )
   dataultimomovimento 
from
   processo p,
   infra_parametro ip 
where
   ip.nome = 'EPROC_TIPO_ESTRUTURA_ORGAO' 
   and p.num_processo in 
   (
      :list
   )
   and 
   (
      p.id_sigilo = 0 
      or id_sigilo is null 
      or exists
      (
         select
            1 
         from
            v_usuario u 
         where
            u.ident_principal = ? 
            and u.sin_ativo = 'S' 
            and sf_verificaacesso(p.num_processo, u.id_usuario) = 0
      )
   )