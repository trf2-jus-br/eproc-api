package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AdvogadoDocumentoGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.AdvogadoDocumentoGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IAdvogadoDocumentoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Pessoa;

public class AdvogadoDocumentoGet implements IAdvogadoDocumentoGet {

	@Override
	public void run(AdvogadoDocumentoGetRequest req, AdvogadoDocumentoGetResponse resp) throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("advogado-documento-get"))) {
			q.setString(1, req.documento);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				Pessoa in = new Pessoa();
				in.id = rs.getString("id");
				in.nome = rs.getString("nome");
				in.documento = rs.getString("documento");
				in.tipodedocumento = rs.getString("tipodedocumento");

				resp.list.add(in);
			}
		}
	}

	@Override
	public String getContext() {
		return "obter documento de advogado";
	}
}
