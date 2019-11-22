select
   concat(date_format(processo_aviso_comunicacao_envio.dth_inclusao, '%Y%m%d'), lpad(processo_aviso_comunicacao_envio.id_aviso, 7, '0')) as id,
   processo_evento.seq_evento as evento,
   processo_evento.dth_evento as data 
from
   processo_aviso_comunicacao_envio 
   inner join
      mni_tipo_comunicacao 
      on processo_aviso_comunicacao_envio.cod_mni_tipo_comunicacao = mni_tipo_comunicacao.cod_mni_tipo_comunicacao 
   inner join
      processo 
      on processo_aviso_comunicacao_envio.id_processo = processo.id_processo 
      and processo.num_processo = ? 
   inner join
      prazo_processo 
      on processo_aviso_comunicacao_envio.id_prazo_processo = prazo_processo.id_prazo_processo 
   inner join
      processo_evento 
      on prazo_processo.id_processo_evento_abriu = processo_evento.id_processo_evento 
   inner join
      evento_judicial 
      on processo_evento.id_evento_judicial = evento_judicial.id_evento_judicial 
   inner join
      v_usuario 
      on processo_aviso_comunicacao_envio.id_usuario_destinatario = v_usuario.id_usuario 
      and v_usuario.ident_principal = ?
      and v_usuario.sin_ativo = 'S' 
      and processo_aviso_comunicacao_envio.sin_ativo = 'S';