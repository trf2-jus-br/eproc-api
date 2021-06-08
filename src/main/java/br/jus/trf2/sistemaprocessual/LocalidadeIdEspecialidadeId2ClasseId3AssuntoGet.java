package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeIdEspecialidadeId2ClasseId3AssuntoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;

public class LocalidadeIdEspecialidadeId2ClasseId3AssuntoGet
		implements ILocalidadeIdEspecialidadeId2ClasseId3AssuntoGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn
						.prepareStatement(Utils.getSQL("localidade-id-especialidade-id2-classe-id3-assunto-get"))) {
//			q.setInt(1, Integer.parseInt(req.id.substring(0, 2)));
//			q.setInt(2, Integer.parseInt(req.id.substring(2)));
//			q.setInt(3, Integer.parseInt(req.id2));
			q.setInt(1, Integer.parseInt(req.id2));
			q.setInt(2, Integer.parseInt(req.id2));
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
		return "obter classes da especialidade da localidade";
	}
}
