package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeIdEspecialidadeGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNome;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeGetResponse;

public class LocalidadeIdEspecialidadeGet implements ILocalidadeIdEspecialidadeGet {

	@Override
	public void run(LocalidadeIdEspecialidadeGetRequest req, LocalidadeIdEspecialidadeGetResponse resp)
			throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("localidade-id-especialidade-get"))) {
			q.setInt(1, Integer.parseInt(req.id.substring(0,2)));
			q.setInt(2, Integer.parseInt(req.id.substring(2)));
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
		return "obter especialidades da localidade";
	}
}
