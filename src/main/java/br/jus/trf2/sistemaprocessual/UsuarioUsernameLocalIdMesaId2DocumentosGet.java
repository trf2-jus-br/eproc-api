package br.jus.trf2.sistemaprocessual;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.SwaggerError;
import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;

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
					lastId = rs.getString("minuta_id");
					d = new MesaDocumento();
					d.dataDeInclusao = rs.getDate("minuta_inclusao");
					d.id = rs.getString("minuta_id");
					d.numeroDoProcesso = rs.getString("processo_numero");
					d.autor = rs.getString("processo_autor");
					d.reu = rs.getString("processo_reu");
					d.numeroDoDocumento = rs.getString("documento_cod");
					d.descricao = rs.getString("minuta_descricao");
					d.status = rs.getString("minuta_status");
					d.descricaoDoStatus = rs.getString("minuta_status_descr");
					d.tipoDoDocumento = rs.getString("documento_tipo");
					d.identificadorDoUsuarioQueIncluiu = rs.getString("usuario_inclusao_ident");
					d.nomeDoUsuarioQueIncluiu = rs.getString("usuario_inclusao_nome");
					d.siglaDaUnidade = rs.getString("unidade_sigla");
					d.conteudo = rs.getString("minuta_conteudo");
					
					// Obtem o texto da minuta no Caringo
					String uuidCas = rs.getString("uuid_cas");
					if (uuidCas != null) {
						HttpURLConnection con = null;
						URL obj = new URL(SwaggerServlet.getProperty("caringo.url") + uuidCas);
						con = (HttpURLConnection) obj.openConnection();

						con.setRequestProperty("User-Agent", "SwaggerServlets");
						con.setRequestMethod("GET");
						String body = null;
						int responseCode = con.getResponseCode();

						if (responseCode >= 400 && responseCode < 600) {
							SwaggerError err = null;
							InputStream errorStream = null;
							String string = null;
							errorStream = con.getErrorStream();
							if (errorStream != null)
								string = SwaggerUtils.convertStreamToString(errorStream);
							err = (SwaggerError) SwaggerUtils.fromJson(string, SwaggerError.class);
							String errormsg = "HTTP ERROR: " + Integer.toString(responseCode);
							if (con.getResponseMessage() != null)
								errormsg = errormsg + " - " + con.getResponseMessage();
							if (err != null && err.errormsg != null)
								errormsg = err.errormsg;
							errormsg = errormsg.replaceAll("\\s+", " ");
							throw new PresentableException(errormsg);
						}
						
						d.conteudo = SwaggerUtils.convertStreamToString(con.getInputStream());
					}
					resp.list.add(d);
					lastId = d.id;
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
