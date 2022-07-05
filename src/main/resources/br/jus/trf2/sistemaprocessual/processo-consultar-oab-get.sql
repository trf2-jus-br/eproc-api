select distinct
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
   dataultimomovimento,
   (
      select
         concat(pn.nome_pessoa, 
         case
            when
               count(*) > 1 
            then
               concat(" +", count(*) - 1) 
            else
               "" 
         end
) 
      from
         processo_parte pp 
         inner join
            tipo_parte tp 
            on tp.cod_tipo_parte = pp.cod_tipo_parte 
            and tp.sin_polo = 'A' 
         inner join
            pessoa pe 
            on pe.id_pessoa = pp.id_pessoa 
         inner join
            pessoa_nome pn 
            on pn.id_pessoa = pe.id_pessoa 
            and pn.seq_nome_pessoa = pe.seq_nome 
      where
         pp.id_processo = p.id_processo 
         and pp.sin_ativo = 'S' 
      order by
         pp.sin_parte_principal = 'S' limit 1 
   )
   as autor, 
   (
      select
         concat(max(pn.nome_pessoa), 
         case
            when
               count(*) > 1 
            then
               concat(" +", count(*) - 1) 
            else
               "" 
         end
) 
      from
         processo_parte pp 
         inner join
            tipo_parte tp 
            on tp.cod_tipo_parte = pp.cod_tipo_parte 
            and tp.sin_polo = 'R' 
         inner join
            pessoa pe 
            on pe.id_pessoa = pp.id_pessoa 
         inner join
            pessoa_nome pn 
            on pn.id_pessoa = pe.id_pessoa 
            and pn.seq_nome_pessoa = pe.seq_nome 
      where
         pp.id_processo = p.id_processo 
         and pp.sin_ativo = 'S' 
   )
   as reu 
from
   processo p inner join 
   processo_parte pp on (pp.id_processo =p.id_processo) inner join
   processo_parte_procurador ppp on (ppp.id_processo_parte =pp.id_processo_parte) inner join
   usuario u on (u.id_usuario = ppp.id_usuario_procurador) inner join
   pessoa pe on (pe.id_pessoa=u.id_pessoa) inner join
   pessoa_nome pn on pn.id_pessoa = pe.id_pessoa
   and pn.seq_nome_pessoa = pe.seq_nome
   left join pessoa_identificacao pi on (pe.id_pessoa = pi.id_pessoa)
   , infra_parametro ip 
where
   status_processo <> 'B' and	
   ip.nome = 'EPROC_TIPO_ESTRUTURA_ORGAO' 
   and 
   (
   pi.ident_principal = ? and pi.tipo_identificacao = 'OAB'
   )
   and 
   (
      p.id_sigilo = 0 
      or p.id_sigilo is null 
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
   limit 5;