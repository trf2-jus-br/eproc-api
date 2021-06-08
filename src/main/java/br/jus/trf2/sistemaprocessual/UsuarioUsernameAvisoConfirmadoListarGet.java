package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisoConfirmadoListarGet;

public class UsuarioUsernameAvisoConfirmadoListarGet implements IUsuarioUsernameAvisoConfirmadoListarGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
//		try (Connection conn = Utils.getConnection();
//				PreparedStatement q = conn.prepareStatement(Utils.getSQL("aviso-confirmado-listar-get"))) {
//			q.setString(1, req.numero);
//			ResultSet rs = q.executeQuery();
//
//			while (rs.next()) {
//				resp.numero = rs.getString("numero");
//				resp.orgao = "JFRJ"; // rs.getString("cpf");
//				resp.unidade = rs.getString("unidade");
//				resp.localNaUnidade = rs.getString("localnaunidade"); // Apresentar isso apenas para o público interno
//				resp.usuarioautorizado = true; // Esse nós ainda precisamos descobrir como fazer para pesquisar?
//				resp.segredodejustica = rs.getBoolean("segredodejustica");
//				resp.segredodejusticadesistema = rs.getBoolean("segredodejusticadesistema");
//				resp.segredodejusticaabsoluto = rs.getBoolean("segredodejusticaabsoluto");
//				resp.eletronico = true;
//				resp.sentenciado = rs.getBoolean("sentenciado");
//				resp.baixado = rs.getBoolean("baixado");
//				resp.cdas = rs.getString("cdas");
//				resp.dataultimomovimento = Utils.formatarDataHoraMinuto(rs.getTimestamp("dataultimomovimento"));
//				break;
//			}
//		}
	}

	@Override
	public String getContext() {
		return "listar avisos confirmados";
	}
}
