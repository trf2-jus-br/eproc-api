package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernamePeticaoIntercorrenteListarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrente;

public class UsuarioUsernamePeticaoIntercorrenteListarGet implements IUsuarioUsernamePeticaoIntercorrenteListarGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn
						.prepareStatement(Utils.getSQL("usuario-username-peticao-intercorrente-listar-get"))) {
			q.setString(1, req.username);
			q.setDate(2, java.sql.Date.valueOf(req.data));
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				PeticaoIntercorrente p = new PeticaoIntercorrente();
				p.classe = rs.getString("id_classe_judicial");
				p.dataprotocolo = rs.getString("dth_inclusao");
				p.protocolo = rs.getString("protocolo");
				p.processo = rs.getString("num_processo");
				p.unidade = rs.getString("sig_orgao");
				resp.list.add(p);
			}
		}
	}

	@Override
	public String getContext() {
		return "listar petições intercorrentes";
	}
}
