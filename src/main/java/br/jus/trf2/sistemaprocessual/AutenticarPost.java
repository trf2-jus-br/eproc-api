package br.jus.trf2.sistemaprocessual;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.PresentableUnloggedException;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AutenticarPostRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AutenticarPostResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IAutenticarPost;

public class AutenticarPost implements IAutenticarPost {

	@Override
	public void run(AutenticarPostRequest req, AutenticarPostResponse resp) throws Exception {
		if (req.login == null)
			throw new PresentableException("É necessário informar o login");
		if (req.senha == null)
			throw new PresentableException("É necessário informar a senha");

		String hash = null;
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("autenticar-post"));
				PreparedStatement q2 = conn.prepareStatement(Utils.getSQL("usuario-web-username-get"))) {
			q.setString(1, req.login);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				hash = rs.getString("hash");
				break;
			}

			if (hash == null)
				throw new PresentableException("Usuário não encontrado");

			String senha = Utils.asHex(Utils.calcSha256(req.senha.getBytes(StandardCharsets.UTF_8)));
			String hashDeTeste = BCrypt.hashpw(senha, hash);

			if (!hashDeTeste.equals(hash))
				throw new PresentableUnloggedException("Usuário ou senha inválidos");

			q2.setString(1, req.login);
			ResultSet rs2 = q2.executeQuery();

			while (rs2.next()) {
				resp.nome = rs2.getString("nome");
				resp.cpf = rs2.getString("cpf");
				resp.email = rs2.getString("email");
				break;
			}
		}
	}

	@Override
	public String getContext() {
		return "autenticar usuário";
	}

	public static void main(String[] args) throws Exception {
		String s = "senha";
		String senha = Utils.asHex(Utils.calcSha256(s.getBytes()));
		System.out.println(senha);
		System.out.println(BCrypt.hashpw(senha, "$2a$12$7cOkE3Y5bEQvd/N2eNZcWeTDdtTKM3k1p4lAYTiarLKOubG8MGEym"));
	}

}
