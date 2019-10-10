select
   pe.dth_evento as data_evento,
   prazo_processo.num_dias_prazo_evento as prazo,
   adddate(pe.dth_evento, interval 10 day) as datalimiteintauto,
   e1.des_evento as evento,
   processo.num_processo as numprocesso,
   (
      select
         assunto_judicial.des_assunto 
      from
         processo pr 
         left join
            assunto_judicial 
            on(pr.id_assunto_principal = assunto_judicial.id_assunto_judicial) 
      where
         pr.id_processo = processo.id_processo 
   )
   as assunto,
   (
      select
         og.des_orgao 
      from
         processo po 
         left join
            orgao og 
            on (og.id_orgao = po.id_orgao_juizo) 
      where
         po.id_processo = processo.id_processo 
   )
   as unidade,
   prazo_processo.status_prazo as status 
from
   prazo_processo 
   inner join
      processo_parte_procurador 
      on prazo_processo.id_processo_parte = processo_parte_procurador.id_processo_parte 
      and processo_parte_procurador.id_usuario_procurador = 
      (
         select
            id_usuario 
         from
            v_usuario vu 
         where
            ident_principal = ?
            and sin_ativo = 'S' 
      )
      and processo_parte_procurador.sin_ativo = 'S' 
   inner join
      (
         processo_parte 
         inner join
            processo 
            on processo_parte.id_processo = processo.id_processo 
            and processo_parte.seq_processo = processo.seq_processo 
      )
      on prazo_processo.id_processo_parte = processo_parte.id_processo_parte 
      and processo_parte.sin_ativo = 'S' 
   inner join
      (
         processo_evento pe 
         inner join
            evento_judicial e1 
            on pe.id_evento_judicial = e1.id_evento_judicial 
            and e1.sin_controle_eletronico = 'S' 
         inner join
            evento_judicial e3 
            on pe.id_evento_judicial = e3.id_evento_judicial 
            and e3.sin_autocomposicao = 'N' 
      )
      on prazo_processo.id_processo_evento_abriu = pe.id_processo_evento 
where
   prazo_processo.status_prazo = 'A' 
   and prazo_processo.data_inicial_prazo is null 
   and (processo.id_sigilo = 0 
   or processo.id_sigilo IS NULL 
   or sf_verificaAcesso(trim(processo.num_processo), processo_parte_procurador.id_usuario_procurador) = 0)
order by
   pe.dth_evento asc,
   processo.num_processo asc