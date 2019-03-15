package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPeticaoInicialTiposDocumentoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoInicialTiposDocumentoGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoInicialTiposDocumentoGetResponse;

public class PeticaoInicialTiposDocumentoGet implements IPeticaoInicialTiposDocumentoGet {

	@Override
	public void run(PeticaoInicialTiposDocumentoGetRequest req, PeticaoInicialTiposDocumentoGetResponse resp)
			throws Exception {
		resp.list = new ArrayList<>();

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
