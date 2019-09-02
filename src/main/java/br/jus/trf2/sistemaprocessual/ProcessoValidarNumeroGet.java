package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.crivano.swaggerservlet.SwaggerServlet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IProcessoValidarNumeroGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ProcessoValidarNumeroGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ProcessoValidarNumeroGetResponse;

public class ProcessoValidarNumeroGet implements IProcessoValidarNumeroGet {

	@Override
	public void run(ProcessoValidarNumeroGetRequest req, ProcessoValidarNumeroGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("processo-validar-numero-get"))) {
			q.setString(1, req.numero);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				resp.numero = rs.getString("numero");
				//resp.orgao = SwaggerServlet.getProperty("orgao.sigla"); // rs.getString("cpf");
				resp.unidade = rs.getString("unidade");
				resp.localNaUnidade = rs.getString("localnaunidade"); // Apresentar isso apenas para o público interno
				resp.usuarioautorizado = true; // Esse nós ainda precisamos descobrir como fazer para pesquisar?
				resp.segredodejustica = rs.getBoolean("segredodejustica");
				resp.segredodejusticadesistema = rs.getBoolean("segredodejusticadesistema");
				resp.segredodejusticaabsoluto = rs.getBoolean("segredodejusticaabsoluto");
				resp.eletronico = true;
				resp.sentenciado = rs.getBoolean("sentenciado");
				resp.baixado = rs.getBoolean("baixado");
				resp.perdecompetencia = rs.getBoolean("perdecompetencia");
				resp.cdas = rs.getString("cdas");
				resp.dataultimomovimento = Utils.formatarDataHoraMinuto(rs.getTimestamp("dataultimomovimento"));
				break;
			}
		}
	}

	@Override
	public String getContext() {
		return "validar número de processo";
	}
}
