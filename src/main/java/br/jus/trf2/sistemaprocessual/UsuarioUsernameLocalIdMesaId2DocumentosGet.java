package br.jus.trf2.sistemaprocessual;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crivano.swaggerservlet.PresentableException;
import com.crivano.swaggerservlet.SwaggerError;
import com.crivano.swaggerservlet.SwaggerUtils;

 //import br.jus.trf2.balcaojus.BalcaojusServlet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IUsuarioUsernameLocalIdMesaId2DocumentosGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.Lembrete;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.MesaDocumento;

public class UsuarioUsernameLocalIdMesaId2DocumentosGet implements IUsuarioUsernameLocalIdMesaId2DocumentosGet {

	@Override
	public void run(Request req, Response resp, SistemaProcessualContext ctx) throws Exception {
		try (Connection conn = Utils.getConnection();
				PreparedStatement q = conn.prepareStatement(Utils.getSQL("usuario-username-minutas"));
				) 
			{
			/* Os lembretes das minutas são agrupados na coluna lembretes, 
			 * é necessário aumentar o a variável do mysql group_concat_max_len 
			 * pra caber tudo na coluna*/
			try(Statement stmt = conn.createStatement();){  
				String sqlSetSession = "SET SESSION group_concat_max_len = 100000"; 
				stmt.execute(sqlSetSession);
			}		
			q.setString(1, req.username);
			ResultSet rs = q.executeQuery();
			resp.list = new ArrayList<>();
			MesaDocumento d = null;
			while (rs.next()) {
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
					if (req.ambiente.equals("prod" ))
						d.conteudo = this.getConteudoMinuta(rs.getString("uuid_cas"));
					d.lembretes = this.getLembretes(rs.getString("lembretes"));
					resp.list.add(d);
									
				
			}
			if (q  != null) q.close();
	        if (conn != null) conn.close();
		}
	}

	private List<Lembrete> getLembretes(String colunaLembretes){
		if (colunaLembretes.isEmpty())
			return null;
			
		List<String> listaLembretes = 
				new ArrayList<>(Arrays.asList(colunaLembretes.split("\\*")));
		
		List<Lembrete> lembretes = new ArrayList<>();
       if (listaLembretes.size()>0)
		for (String registroLembrete : listaLembretes) {
            
            String[] colunasLembrete = registroLembrete.trim().split("\\|");
            Lembrete  lembrete = new Lembrete();
            for (int i=0; i< colunasLembrete.length; i++) {
            	switch(i) {
            	  case 0:
            		lembrete.id = colunasLembrete[0];
            	    break;
            	  case 1:
            		lembrete.dataDeInclusao = Utils.formatarData(colunasLembrete[1]);
            	    break;
            	  case 2:
            		lembrete.conteudo = colunasLembrete[2];
	            	break;
	              case 3:
	            	lembrete.identificadorDoUsuario = colunasLembrete[3];
	            	break;
	              case 4:
	            	  lembrete.nomeDoUsuario = colunasLembrete[4];
		             break;
            	}
            }	
            	lembretes.add(lembrete);
            
        }
       
       return lembretes ;
	
	}
	
	private String getConteudoMinuta(String uuidCas) throws Exception{
		// Obtem o texto da minuta no Caringo
		
		if (uuidCas == null)
		  return null;
		
			HttpURLConnection con = null;
			URL obj = new URL(EprocServlet.INSTANCE.getProperty("caringo.url") + uuidCas);
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
			return SwaggerUtils.convertStreamToString(con.getInputStream());
		
	}
	@Override
	public String getContext() {
		return "complementar informações de minutas";
	}
}
