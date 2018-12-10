package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernameGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameGetResponse;

public class UsuarioWebUsernameGet implements IUsuarioWebUsernameGet {

	@Override
	public void run(UsuarioWebUsernameGetRequest req, UsuarioWebUsernameGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("usuario-web-username-get"))) {
			q.setString(1, req.username);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				resp.nome = rs.getString("nome");
				resp.cpf = rs.getString("cpf");
				resp.email = rs.getString("email");
				break;
			}
		}
	}

	@Override
	public String getContext() {
		return "obter informações de usuário web";
	}
}
