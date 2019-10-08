package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameLocalIdMesaId2DocumentosGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Lembrete;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.MesaDocumento;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2DocumentosGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.UsuarioUsernameLocalIdMesaId2DocumentosGetResponse;

public class UsuarioUsernameLocalIdMesaId2DocumentosGet implements IUsuarioUsernameLocalIdMesaId2DocumentosGet {

	@Override
	public void run(UsuarioUsernameLocalIdMesaId2DocumentosGetRequest req,
			UsuarioUsernameLocalIdMesaId2DocumentosGetResponse resp) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("usuario-username-minutas"))) {
			q.setString(1, req.username);
			ResultSet rs = q.executeQuery();

			resp.list = new ArrayList<>();
			String lastId = null;
			MesaDocumento d = null;
			while (rs.next()) {
				if (!rs.getString("minuta_id").equals(lastId)) {
					d = new MesaDocumento();
					d.dataDeInclusao = rs.getDate("minuta_inclusao");
					d.id = rs.getString("minuta_id");
					d.numeroDoProcesso = rs.getString("processo_numero");
					d.numeroDoDocumento = rs.getString("documento_cod");
					d.descricao = rs.getString("minuta_descricao");
					d.status = rs.getString("minuta_status");
					d.descricaoDoStatus = rs.getString("minuta_status_descr");
					d.tipoDoDocumento = rs.getString("documento_tipo");
					d.identificadorDoUsuarioQueIncluiu = rs.getString("usuario_inclusao_ident");
					d.nomeDoUsuarioQueIncluiu = rs.getString("usuario_inclusao_nome");
					d.conteudo = rs.getString("minuta_conteudo");
					resp.list.add(d);
				}
				if (rs.getString("lembrete_conteudo") != null) {
					Lembrete lembrete = new Lembrete();
					lembrete.id = rs.getString("lembrete_id");
					lembrete.dataDeInclusao = rs.getDate("lembrete_inclusao");
					lembrete.conteudo = rs.getString("lembrete_conteudo");
					lembrete.identificadorDoUsuario = rs.getString("lembrete_usuario");
					lembrete.nomeDoUsuario = rs.getString("lembrete_nome");
					if (d.lembretes == null)
						d.lembretes = new ArrayList<>();
					d.lembretes.add(lembrete);
				}
			}
		}
	}

	@Override
	public String getContext() {
		return "complementar informações de minutas";
	}
}
