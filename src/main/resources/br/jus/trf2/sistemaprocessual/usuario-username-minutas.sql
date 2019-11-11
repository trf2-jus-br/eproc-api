select
  m.dth_inclusao minuta_inclusao,
  m.id_minuta minuta_id,
  m.cod_documento documento_cod,
  m.descricao_minuta minuta_descricao,
  m.cod_status_minuta minuta_status,
  sm.des_status_minuta minuta_status_descr,
  p.num_processo processo_numero,
  td.des_tipo_documento documento_tipo,
  v.conteudo as minuta_conteudo,
  ui.ident_principal usuario_inclusao_ident,
  pni.nome_pessoa usuario_inclusao_nome,
  ml.dth_inclusao lembrete_inclusao,
  ml.id_minuta_lembrete lembrete_id,
  ml.lembrete lembrete_conteudo,
  ul.ident_principal lembrete_usuario,
  pnl.nome_pessoa lembrete_nome,
  minuta_bloqueio.id_usuario_bloqueio as idusuariobloqueio 
from
  minuta m 
  left join
    minuta_bloqueio 
    on m.id_minuta = minuta_bloqueio.id_minuta 
  inner join
    versao_conteudo v 
    on v.id_versao_conteudo = m.id_versao_conteudo_ultima 
  inner join
    versao_conteudo vv 
    on vv.id_versao_conteudo = m.id_versao_modelo_matriz 
  inner join
    modelo_matriz mm 
    on mm.id_modelo_matriz = vv.id_modelo_matriz 
  inner join
    tipo_documento_minuta tdm 
    on mm.cod_tipo_documento_minuta = tdm.cod_tipo_documento_minuta 
  inner join
    tipo_documento td 
    on tdm.cod_tipo_documento = td.cod_tipo_documento 
  inner join
    processo p 
    on p.id_processo = m.id_processo 
  inner join
    v_usuario u 
    on m.id_usuario_assinante_indicado = u.id_usuario 
    and ident_principal = ? 
    and u.cod_tipo_usuario = 'M' 
    and u.sin_ativo = 'S' 
  inner join
    v_usuario ui 
    on m.id_usuario_inclusao = ui.id_usuario 
  inner join
    pessoa pi 
    on ui.id_pessoa = pi.id_pessoa 
  inner join
    pessoa_nome pni 
    on pi.id_pessoa = pni.id_pessoa 
    and pi.seq_nome = pni.seq_nome_pessoa 
  inner join
    status_minuta sm 
    on m.cod_status_minuta = sm.cod_status_minuta 
  left outer join
    minuta_lembrete ml 
    on ml.id_minuta = m.id_minuta 
    and ml.sin_ativo = 'S' 
  left outer join
    v_usuario ul 
    on ml.id_usuario_inclusao = ul.id_usuario 
  left outer join
    pessoa pl 
    on ul.id_pessoa = pl.id_pessoa 
  left outer join
    pessoa_nome pnl 
    on pl.id_pessoa = pnl.id_pessoa 
    and pl.seq_nome = pnl.seq_nome_pessoa 
where
  m.cod_status_minuta in 
  (
    4
  )
  and minuta_bloqueio.id_usuario_bloqueio is null 
order by
  m.dth_inclusao,
  m.id_minuta