package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;



import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNumerosGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Processo;

public class UsuarioUsernameProcessoNumerosGet implements IUsuarioUsernameProcessoNumerosGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		String[] list = req.numeros.split(",");
		char[] markers = new char[list.length * 2 - 1];
		for (int i = 0; i < markers.length; i++)
			markers[i] = ((i & 1) == 0 ? '?' : ',');

		String statement = Utils.getSQL("processo-validar-numero-get");
		statement = statement.replace(":list", new String(markers));
		DecimalFormat formatoNumeroProcessoEproc = new DecimalFormat("00000000000000000000");
		try (Connection conn = Utils.getConnection(); PreparedStatement q = conn.prepareStatement(statement)) {
			int i = 1;
			for (String s : list)
				q.setString(i++, s);
			q.setString(i, req.username);
			ResultSet rs = q.executeQuery();
			

			resp.list = new ArrayList<>();
			while (rs.next()) {
				Processo p = new Processo();
				p.numero = formatoNumeroProcessoEproc.format(rs.getBigDecimal("numero"));
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
			if (q  != null) q.close();
	        if (conn != null) conn.close();
		}
	}

	@Override
	public String getContext() {
		return "validar número de processo";
	}
}
