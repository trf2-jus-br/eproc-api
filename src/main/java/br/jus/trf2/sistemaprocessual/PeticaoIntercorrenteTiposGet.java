package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPeticaoIntercorrenteTiposGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.TipoPeticaoIntercorrente;

public class PeticaoIntercorrenteTiposGet implements IPeticaoIntercorrenteTiposGet {

	@Override
	public void run(PeticaoIntercorrenteTiposGetRequest req, PeticaoIntercorrenteTiposGetResponse resp)
			throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("peticao-intercorrente-tipos-documento-get"))) {


			q.setString(1, req.codusuario);
			q.setString(2, req.numprocesso);

			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				TipoPeticaoIntercorrente in = new TipoPeticaoIntercorrente();
				in.id = rs.getString("id");
				in.descricao = rs.getString("nome");
				resp.list.add(in);
			}
		}
	}

	@Override
	public String getContext() {
		return "obter lista de tipos de petição intercorrente";
	}
	

	
}
