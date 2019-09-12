package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.TipoPeticaoIntercorrente;

public class UsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGet
		implements IUsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGet {

	@Override
	public void run(UsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGetRequest req,
			UsuarioUsernameProcessoNumeroPeticaoIntercorrenteTiposGetResponse resp) throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn
						.prepareStatement(Utils.getSQL("peticao-intercorrente-tipos-documento-get"))) {

			q.setString(1, req.username);
			q.setString(2, req.numero);

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
