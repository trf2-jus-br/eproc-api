package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IOrgaoPublicoListarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.OrgaoPublicoListarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.OrgaoPublicoListarGetResponse;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Pessoa;

public class OrgaoPublicoListarGet implements IOrgaoPublicoListarGet {

	@Override
	public void run(OrgaoPublicoListarGetRequest req, OrgaoPublicoListarGetResponse resp) throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("orgao-publico-listar-get"))) {
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
		return "obter lista de órgãos públicos";
	}
}
