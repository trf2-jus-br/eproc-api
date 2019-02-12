package br.jus.trf2.sistemaprocessual;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import com.crivano.swaggerservlet.ISwaggerModel;
import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.PresentableUnloggedException;
import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernameGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Usuario;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameGetResponse;

public class UsuarioWebUsernameGet implements IUsuarioWebUsernameGet {

	@Override
	public void run(UsuarioWebUsernameGetRequest req, UsuarioWebUsernameGetResponse resp) throws Exception {
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
				PreparedStatement q2 = conn.prepareStatement(Utils.getSQL("usuario-web-username-get"))) {
			q.setString(1, login);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
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

			while (rs2.next()) {
				resp.nome = rs2.getString("nome");
				resp.cpf = rs2.getString("cpf");
				resp.email = rs2.getString("email");
				break;
			}

			resp.usuarios = new ArrayList<>();
			Usuario u = new Usuario();
			u.orgao = "JFRJ";
			u.codusu = null;
			u.codusuweb = null;
			u.codunidade = null;
			u.nome = resp.nome;
			u.cpf = resp.cpf;
			u.email = resp.email;
			u.perfil = null;
			resp.usuarios.add(u);
		}
	}

	@Override
	public String getContext() {
		return "obter informações de usuário web";
	}
}
