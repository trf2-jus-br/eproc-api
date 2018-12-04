package br.jus.trf2.sistemaprocessual;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Query;

import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.PresentableUnloggedException;
import com.crivano.swaggerservlet.SwaggerUtils;

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

		String senha = SwaggerUtils.base64Encode(Utils.calcSha256(req.senha.getBytes(StandardCharsets.UTF_8)));

		Query q = PersistenceManager.INSTANCE.getEntityManager().createNativeQuery(Utils.getSQL("autenticar-post"));
		q.setParameter("login", req.login);
		List<Object[]> l = q.getResultList();

		if (l == null || l.size() == 0)
			throw new PresentableUnloggedException("Usuário ou senha inválidos");

		resp.cpf = null;
		resp.email = "test@example.com";
		Object[] r = l.get(0);
		resp.nome = (String) r[0];
	}

	@Override
	public String getContext() {
		return "autenticar usuário";
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String s = "senha";
		String senha = SwaggerUtils.base64Encode(Utils.calcSha256(s.getBytes(StandardCharsets.UTF_8)));
		System.out.println(senha);

	}

}
