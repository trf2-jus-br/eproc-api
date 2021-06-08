package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNumeroConsultarGet;

public class UsuarioUsernameProcessoNumeroConsultarGet implements IUsuarioUsernameProcessoNumeroConsultarGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("processo-consultar-numero-get"))) {
			q.setString(1, req.numero);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				resp.magistrado = rs.getString("magistrado");
				resp.numero = req.numero;
				break;
			}
		}
	}

	@Override
	public String getContext() {
		return "consultar número de processo";
	}
}
