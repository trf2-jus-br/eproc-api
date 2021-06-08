package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPeticaoInicialTiposDocumentoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;

public class PeticaoInicialTiposDocumentoGet implements IPeticaoInicialTiposDocumentoGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("peticao-inicial-tipos-documento-get"))) {
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
		return "obter lista de tipos de documento para peticao inicial";
	}
}
