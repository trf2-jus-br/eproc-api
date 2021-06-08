package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;

public class LocalidadeGet implements ILocalidadeGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("localidade-get"))) {
			// q.setString(1, req.numero);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				IdNome in = new IdNome();
				in.id = rs.getString("id");
				in.nome = rs.getString("nome");
				resp.list.add(in);
			}
		}
	}

	@Override
	public String getContext() {
		return "obter localidade";
	}
}
