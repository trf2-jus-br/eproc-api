package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ClasseCNJ;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ILocalidadeIdEspecialidadeId2ClasseGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IdNomeClasseCNJ;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeId2ClasseGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.LocalidadeIdEspecialidadeId2ClasseGetResponse;

public class LocalidadeIdEspecialidadeId2ClasseGet implements ILocalidadeIdEspecialidadeId2ClasseGet {

	@Override
	public void run(LocalidadeIdEspecialidadeId2ClasseGetRequest req,
			LocalidadeIdEspecialidadeId2ClasseGetResponse resp) throws Exception {
		resp.list = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("localidade-id-especialidade-id2-classe-get"))) {
			q.setInt(1, Integer.parseInt(req.id.substring(0,2)));
			q.setInt(2, Integer.parseInt(req.id.substring(2)));
			q.setInt(3, Integer.parseInt(req.id2));
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				IdNomeClasseCNJ in = new IdNomeClasseCNJ();
				in.id = rs.getString("id");
				in.nome = rs.getString("nome");
				in.classecnj = new ClasseCNJ();
				in.classecnj.codigo = rs.getDouble("classecnj");
				in.classecnj.descricao = rs.getString("nome");
				in.classecnj.ativo = true;
				in.valordacausaobrigatorio = false;
				resp.list.add(in);
			}
		}
	}

	@Override
	public String getContext() {
		return "obter classes da especialidade da localidade";
	}
}
