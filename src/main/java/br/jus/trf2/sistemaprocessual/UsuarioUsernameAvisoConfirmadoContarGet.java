package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameAvisoConfirmadoContarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.QuantidadeConfirmada;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoConfirmadoContarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameAvisoConfirmadoContarGetResponse;

public class UsuarioUsernameAvisoConfirmadoContarGet implements IUsuarioUsernameAvisoConfirmadoContarGet {

	@Override
	public void run(UsuarioUsernameAvisoConfirmadoContarGetRequest req,
			UsuarioUsernameAvisoConfirmadoContarGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("aviso-confirmado-contar-get"))) {
			String idPessoa = UsuarioUsernameGet.getIdPessoaFromUsername(conn, req.username);

			q.setString(1, idPessoa);
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			while (rs.next()) {
				QuantidadeConfirmada r = new QuantidadeConfirmada();
				r.data = Utils.formatarData(rs.getTimestamp("yyyymmdd"));
				r.quantidadeDoGrupoPorConfirmacao = rs.getString("quantidadeDoGrupoPorConfirmacao");
				r.quantidadeDoGrupoPorOmissao = rs.getString("quantidadeDoGrupoPorOmissao");
				r.quantidadeDoUsuarioPorConfirmacao = rs.getString("quantidadeDoUsuarioPorConfirmacao");
				r.quantidadeDoUsuarioPorOmissao = rs.getString("quantidadeDoUsuarioPorOmissao");
				resp.list.add(r);
			}
		}
	}

	@Override
	public String getContext() {
		return "listar avisos confirmados";
	}
}
