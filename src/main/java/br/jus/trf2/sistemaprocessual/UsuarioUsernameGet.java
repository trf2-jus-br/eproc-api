package br.jus.trf2.sistemaprocessual;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.PresentableUnloggedException;
import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameGetResponse;

public class UsuarioUsernameGet implements IUsuarioUsernameGet {

	@Override
	public void run(UsuarioUsernameGetRequest req, UsuarioUsernameGetResponse resp) throws Exception {
		String login;
		String password;
		String auth = SwaggerServlet.getHttpServletRequest().getHeader("Authorization");
		if (auth == null)
			throw new Exception("Autorização necessária");
		String authBasic = auth.split("\\s+")[1];
		String[] authBasicParts = new String(SwaggerUtils.base64Decode(authBasic)).split(":");
		login = authBasicParts[0];
		password = authBasicParts[1];

		if (login == null)
			throw new PresentableException("É necessário informar o login");
		if (password == null)
			throw new PresentableException("É necessário informar a senha");

		String hash = null;
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("autenticar-post"));
				PreparedStatement q2 = conn.prepareStatement(Utils.getSQL("usuario-username-get"))) {
			q.setString(1, login);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				resp.codusu = rs.getString("codusu");
				hash = rs.getString("hash");
				break;
			}

			if (hash == null)
				throw new PresentableException("Usuário não encontrado");

			String senha = Utils.asHex(Utils.calcSha256(password.getBytes(StandardCharsets.UTF_8)));
			String hashDeTeste = BCrypt.hashpw(senha, hash);

			if (!hashDeTeste.equals(hash))
				throw new PresentableUnloggedException("Usuário ou senha inválidos");

			q2.setString(1, login);
			ResultSet rs2 = q2.executeQuery();

			// TODO: lançar exceção se não houver nome, cpf e email
			while (rs2.next()) {
				resp.interno = rs2.getBoolean("usuinterno");
				resp.codusu = rs2.getString("codusu");
				if (resp.interno) 
					resp.codunidade = rs2.getString("codunidade");
				resp.nome = rs2.getString("nome");
				resp.cpf = rs2.getString("cpf");
				resp.email = rs2.getString("email");
				resp.perfil = Utils.slugify(rs2.getString("perfil"), true, false);
				break;
			}
		}
	}

	public static String getIdPessoaFromUsername(Connection conn, String login) throws SQLException {
		try (PreparedStatement q = conn.prepareStatement(Utils.getSQL("autenticar-post"))) {
			q.setString(1, login);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				return rs.getString("codusu");
			}
			return null;
		}
	}

	@Override
	public String getContext() {
		return "obter informações de usuário web";
	}
}
