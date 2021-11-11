package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNomeparteConsultarNomeGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Processo;

public class UsuarioUsernameProcessoNomeparteConsultarNomeGet 
implements IUsuarioUsernameProcessoNomeparteConsultarNomeGet {

	@Override
	public String getContext() {
		return "consultar processo pelo nome da parte";
	}

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		

		try (Connection conn = Utils.getConnection(); 
			PreparedStatement q = conn.prepareStatement(Utils.getSQL("processo-consultar-nome-get"))) {
			q.setString(1, req.nomeparte);
			q.setString(2, req.username);
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			while (rs.next()) {
				Processo p = new Processo();
				p.numero = rs.getString("numero");
				p.orgao = EprocServlet.INSTANCE.getProperty("orgao.sigla");
				p.unidade = rs.getString("unidade");
				p.localNaUnidade = rs.getString("localnaunidade"); // Apresentar
																	// isso
																	// apenas
																	// para o
																	// público
																	// interno
				p.usuarioautorizado = true; // Esse nós ainda precisamos
											// descobrir como fazer para
											// pesquisar?
				p.segredodejustica = rs.getBoolean("segredodejustica");
				p.segredodejusticadesistema = rs.getBoolean("segredodejusticadesistema");
				p.segredodejusticaabsoluto = rs.getBoolean("segredodejusticaabsoluto");
				p.eletronico = true;
				p.sentenciado = rs.getBoolean("sentenciado");
				p.baixado = rs.getBoolean("baixado");
				p.perdecompetencia = rs.getBoolean("perdecompetencia");
				p.cdas = rs.getString("cdas");
				p.dataultimomovimento = Utils.formatarDataHoraMinuto(rs.getTimestamp("dataultimomovimento"));
				p.autor = rs.getString("autor");
				p.reu = rs.getString("reu");
				resp.list.add(p);
			}
		}

	}
}
