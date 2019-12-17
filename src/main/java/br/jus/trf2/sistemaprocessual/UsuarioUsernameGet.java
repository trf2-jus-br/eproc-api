package br.jus.trf2.sistemaprocessual;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.crivano.swaggerservlet.ISwaggerPublicMethod;
import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.PresentableUnloggedException;
import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameGetResponse;

public class UsuarioUsernameGet implements IUsuarioUsernameGet, ISwaggerPublicMethod {

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
		String hashFunction = null;
		String hashPersistencia = null;
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("autenticar-post"));
				PreparedStatement q2 = conn.prepareStatement(Utils.getSQL("usuario-username-get"))) {
			q.setString(1, login);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				resp.codusu = rs.getString("codusu");
				hash = rs.getString("hash");
				hashFunction = rs.getString("hash_function");
				hashPersistencia = rs.getString("hash_persistencia");
				break;
			}

			if (hash == null)
				throw new PresentableUnloggedException("Usuário não encontrado");

			String hashDeTeste = computeHash(password, hash, hashFunction, "HEX", hashPersistencia);
			if (!hashDeTeste.equals(hash))
				if (!verificarEstrategiasAlternativasDeSenha(password, hash))
					throw new PresentableUnloggedException("Usuário ou senha inválidos");

			q2.setString(1, login);
			ResultSet rs2 = q2.executeQuery();

			// TODO: lançar exceção se não houver nome, cpf e email
			while (rs2.next()) {
				resp.interno = rs2.getBoolean("usuinterno");
				resp.codusu = rs2.getString("codusu");
				resp.codentidade = rs2.getString("codentidade");
				resp.entidade = rs2.getString("entidade");
				resp.codunidade = rs2.getString("codunidade");
				resp.unidade = rs2.getString("unidade");
				resp.nome = rs2.getString("nome");
				resp.cpf = rs2.getString("cpf");
				resp.email = rs2.getString("email");
				resp.perfil = Utils.slugify(rs2.getString("perfil"), true, false);
				return;
			}

			throw new PresentableUnloggedException(
					"Não foi possível localizar informações para o usuário '" + req.username + "'");
		}
	}

	public static boolean verificarEstrategiasAlternativasDeSenha(String password, String hash)
			throws NoSuchAlgorithmException, PresentableException {
		for (String c : new String[] { "REG", "UPPER", "LOWER" }) {
			for (String h : new String[] { "MD5", "SHA1", "SHA256" }) {
				for (String e : new String[] { "HEX", "BASE64" }) {
					String p = c.equals("UPPER") ? password.toUpperCase()
							: c.equals("LOWER") ? password.toLowerCase() : password;
					String computed = computeHash(p, hash, h, e, "BCRYPT");
					if (computed.equals(hash))
						return true;
				}
			}
		}
		return false;
	}

	public static String computeHash(String password, String hash, String hashFunction, String encode,
			String hashPersistencia) throws NoSuchAlgorithmException, PresentableException {
		byte[] ab = null;

		switch (hashFunction) {
		case "MD5":
			ab = Utils.calcMd5(password.getBytes(StandardCharsets.UTF_8));
			break;
		case "SHA1":
			ab = Utils.calcSha1(password.getBytes(StandardCharsets.UTF_8));
			break;
		case "SHA256":
			ab = Utils.calcSha256(password.getBytes(StandardCharsets.UTF_8));
			break;
		case "CRC":
		case "BCRYPT":
		case "SHA1 (TRF2)":
		default:
			throw new PresentableException("Método de hash não implementado: " + hashFunction);
		}

		String senha = null;
		switch (encode) {
		case "HEX":
			senha = Utils.asHex(ab);
			break;
		case "BASE64":
			senha = SwaggerUtils.base64Encode(ab);
			break;
		default:
			throw new PresentableException("Método de encode não implementado: " + encode);
		}

		String hashDeTeste = null;
		switch (hashPersistencia) {
		case "BCRYPT":
			hashDeTeste = BCrypt.hashpw(senha, hash);
			break;
		case "MD5":
		case "SHA1":
		case "SHA256":
		case "CRC":
		case "SHA1 (TRF2)":
		default:
			throw new PresentableException("Método de persistência de hash não implementado: " + hashPersistencia);
		}
		return hashDeTeste;
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
