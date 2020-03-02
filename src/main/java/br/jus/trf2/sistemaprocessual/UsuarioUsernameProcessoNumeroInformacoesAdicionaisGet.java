package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.CDA;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameProcessoNumeroInformacoesAdicionaisGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroInformacoesAdicionaisGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameProcessoNumeroInformacoesAdicionaisGetResponse;

public class UsuarioUsernameProcessoNumeroInformacoesAdicionaisGet
		implements IUsuarioUsernameProcessoNumeroInformacoesAdicionaisGet {

	@Override
	public void run(UsuarioUsernameProcessoNumeroInformacoesAdicionaisGetRequest req,
			UsuarioUsernameProcessoNumeroInformacoesAdicionaisGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("processo-informacoes-adicionais-cdas"))) {
			q.setString(1, req.numero);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				if (resp.cdas == null)
					resp.cdas = new ArrayList<>();
				CDA cda = new CDA();
				cda.numero = rs.getString("numero_cda");
				cda.processoadministrativo = rs.getString("num_processo_adm");
				cda.status = rs.getString("status");
				cda.grupo = rs.getString("grupo_status");
				cda.codigotributo = rs.getString("codigo_tributo_fiscal");
				cda.tributo = rs.getString("tributo");
				cda.valor = rs.getDouble("valor_cda");
				cda.valorufir = rs.getDouble("valor_ufir");
				cda.dataorigem = rs.getDate("data_origem");
				cda.datainclusao = rs.getTimestamp("dth_inclusao");
				resp.cdas.add(cda);
			}
		}
	}

	@Override
	public String getContext() {
		return "consultar número de processo";
	}
}
