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
						Utils.getSQL("usuario-username-processo-numero-peticao-intercorrente-ident-encerra-prazos"))) {

			q.setString(1, req.username);
			q.setString(2, req.numero);
			q.setString(3, req.username);
			q.setString(4, req.numero);
			q.setString(5, req.username);
			q.setString(6, req.numero);
			q.setString(7, req.username);

			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				TipoPeticaoIntercorrente in = new TipoPeticaoIntercorrente();
				in.id = rs.getString("id");
				in.descricao = rs.getString("nome");
				resp.tipos.add(in);
			}

			q2.setString(1, req.numero);
			q2.setString(2, req.username);
			q2.setString(3, req.numero);
			q2.setString(4, req.username);

			ResultSet rs2 = q2.executeQuery();

			while (rs2.next()) {
				resp.identencerraprazos = rs2.getString("identEncerraPrazos");
			}
		}
	}

	@Override
	public String getContext() {
		return "obter lista de tipos de petição intercorrente";
	}

}
