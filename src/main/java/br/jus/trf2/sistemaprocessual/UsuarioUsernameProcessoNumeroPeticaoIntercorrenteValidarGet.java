package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.TipoPeticaoIntercorrente;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGetResponse;

public class UsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGet
		implements IUsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGet {

	@Override
	public void run(UsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGetRequest req,
			UsuarioUsernameProcessoNumeroPeticaoIntercorrenteValidarGetResponse resp) throws Exception {
		resp.tipos = new ArrayList<>();

		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(
						Utils.getSQL("usuario-username-processo-numero-peticao-intercorrente-tipos-documento-get"));
				PreparedStatement q2 = conn.prepareStatement(
						Utils.getSQL("usuario-username-processo-numero-peticao-intercorrente-ident-encerra-prazos"));
				PreparedStatement q3 = conn.prepareStatement(
						Utils.getSQL("usuario-username-processo-numero-peticao-intercorrente-sigilo"))) {

			q.setString(1, req.username);
			q.setString(2, req.numero);

			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				TipoPeticaoIntercorrente in = new TipoPeticaoIntercorrente();
				in.id = rs.getString("id");
				in.descricao = rs.getString("nome");
				resp.tipos.add(in);
			}

			q2.setString(1, req.username);
			q2.setString(2, req.username);
			q2.setString(3, req.numero);

			ResultSet rs2 = q2.executeQuery();

			StringBuilder sb = new StringBuilder();
			while (rs2.next()) {
				if (sb.length() > 0)
					sb.append(",");
				sb.append(rs2.getString("identEncerraPrazos"));
			}
			if (sb.length() > 0)
				resp.identencerraprazos = sb.toString();

			q3.setString(1, req.username);
			q3.setString(2, req.numero);

			ResultSet rs3 = q3.executeQuery();

			while (rs3.next()) {
				resp.sigilo = rs3.getDouble("sigilo");
				resp.parte = rs3.getBoolean("parte");
			}

		}
	}

	@Override
	public String getContext() {
		return "obter lista de tipos de petição intercorrente";
	}

}
