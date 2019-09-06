package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.crivano.swaggerservlet.SwaggerServlet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Contagem;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernamePeticaoIntercorrenteContarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernamePeticaoIntercorrenteContarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernamePeticaoIntercorrenteContarGetResponse;

public class UsuarioUsernamePeticaoIntercorrenteContarGet implements IUsuarioUsernamePeticaoIntercorrenteContarGet {

	@Override
	public void run(UsuarioUsernamePeticaoIntercorrenteContarGetRequest req,
			UsuarioUsernamePeticaoIntercorrenteContarGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn
						.prepareStatement(Utils.getSQL("usuario-username-peticao-intercorrente-contar-get"))) {
			q.setString(1, req.username);
			q.setInt(2, Integer.parseInt(req.dias));
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			while (rs.next()) {
				Contagem c = new Contagem();
				c.data = rs.getString("data");
				c.quantidade = rs.getString("qtd");
				resp.list.add(c);
			}
		}
	}

	@Override
	public String getContext() {
		return "contar petições intercorrentes";
	}
}
