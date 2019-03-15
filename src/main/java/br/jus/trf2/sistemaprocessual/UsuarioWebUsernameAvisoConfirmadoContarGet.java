package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioWebUsernameAvisoConfirmadoContarGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.QuantidadeConfirmada;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoConfirmadoContarGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioWebUsernameAvisoConfirmadoContarGetResponse;

public class UsuarioWebUsernameAvisoConfirmadoContarGet implements IUsuarioWebUsernameAvisoConfirmadoContarGet {

	@Override
	public void run(UsuarioWebUsernameAvisoConfirmadoContarGetRequest req,
			UsuarioWebUsernameAvisoConfirmadoContarGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("aviso-confirmado-contar-get"))) {
			String idPessoa = UsuarioWebUsernameGet.getIdPessoaFromUsername(conn, req.username);

			q.setString(1, idPessoa);
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			while (rs.next()) {
				QuantidadeConfirmada r = new QuantidadeConfirmada();
				r.data = Utils.formatarDataHoraMinuto(rs.getTimestamp("data"));
				r.quantidadeDoGrupoPorConfirmacao = rs.getString("quantidadeDoGrupoPorConfirmacao");
				r.quantidadeDoGrupoPorOmissao = rs.getString("quantidadeDoGrupoPorOmissao");
				r.quantidadeDoUsuarioPorConfirmacao = rs.getString("quantidadeDoUsuarioPorConfirmacao");
				r.quantidadeDoUsuarioPorOmissao = rs.getString("quantidadeDoUsuarioPorOmissao");
				break;
			}
		}
	}

	@Override
	public String getContext() {
		return "listar avisos confirmados";
	}
}
