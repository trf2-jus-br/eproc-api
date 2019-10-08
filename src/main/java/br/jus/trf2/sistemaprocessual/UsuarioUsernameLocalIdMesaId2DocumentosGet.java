package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameLocalIdMesaId2DocumentosGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.MesaDocumento;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2DocumentosGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2DocumentosGetResponse;

public class UsuarioUsernameLocalIdMesaId2DocumentosGet implements IUsuarioUsernameLocalIdMesaId2DocumentosGet {

	@Override
	public void run(UsuarioUsernameLocalIdMesaId2DocumentosGetRequest req,
			UsuarioUsernameLocalIdMesaId2DocumentosGetResponse resp) throws Exception {
		String[] list = req.ids.split(",");
		char[] markers = new char[list.length * 2 - 1];
		for (int i = 0; i < markers.length; i++)
			markers[i] = ((i & 1) == 0 ? '?' : ',');

		String statement = Utils.getSQL("usuario-username-mesa-complemento");
		statement = statement.replace(":list", new String(markers));
		try (Connection conn = Utils.getConnection(); PreparedStatement q = conn.prepareStatement(statement)) {
			q.setString(1, req.username);
			int i = 2;
			for (String s : list)
				q.setString(i++, s);
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			while (rs.next()) {
				MesaDocumento d = new MesaDocumento();
				d.id = rs.getString("id");
				d.numerodoprocesso = rs.getString("numero");
				d.conteudo = rs.getString("conteudo");
				resp.list.add(d);
			}
		}
	}

	@Override
	public String getContext() {
		return "complementar informações de minutas";
	}
}
