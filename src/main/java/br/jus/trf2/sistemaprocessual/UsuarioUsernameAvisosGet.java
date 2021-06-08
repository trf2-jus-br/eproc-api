package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Aviso;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisosGet;

public class UsuarioUsernameAvisosGet implements IUsuarioUsernameAvisosGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("usuario-username-avisos-get"))) {
			q.setString(1, req.username);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				Aviso i = new Aviso();
				i.processo = rs.getString("numprocesso");
				// i.idAviso = rs.getString("idaviso");
				i.dataAviso = rs.getTimestamp("data_evento");
				// i.tipo = rs.getString("tipo");
				i.unidade = rs.getString("unidade");
				i.unidadeNome = null;
				i.unidadeTipo = null;
				i.orgao = EprocServlet.INSTANCE.getProperty("orgao.sigla");

				i.localidade = null;
				i.teor = null;
				i.eventoIntimacao = rs.getString("evento");
				i.motivoIntimacao = null;
				i.numeroPrazo = rs.getString("prazo");
				i.tipoPrazo = null;
				i.multiplicadorPrazo = null;
				i.dataLimiteIntimacaoAutomatica = rs.getTimestamp("datalimiteintauto");
				i.assunto = rs.getString("assunto");
				i.dataFinalPrazo = rs.getTimestamp("data_prazo_final");

				resp.list.add(i);
			}
		}
	}

	@Override
	public String getContext() {
		return "listar avisos confirmados";
	}
}
