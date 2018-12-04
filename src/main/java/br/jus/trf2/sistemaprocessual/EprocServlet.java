package br.jus.trf2.sistemaprocessual;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;
import com.crivano.swaggerservlet.dependency.TestableDependency;
import com.crivano.swaggerservlet.property.PrivateProperty;
import com.crivano.swaggerservlet.property.PublicProperty;
import com.crivano.swaggerservlet.property.RestrictedProperty;

public class EprocServlet extends SwaggerServlet {
	private static final long serialVersionUID = 1756711359239182178L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		super.setAPI(ISistemaProcessual.class);

		super.setActionPackage("br.jus.trf2.sistemaprocessual");

		super.addProperty(new PublicProperty("balcaovirtual.env"));
		super.addProperty(new PublicProperty("balcaovirtual.wootric.token"));
		super.addProperty(new RestrictedProperty("balcaovirtual.ws.processual.url"));
		super.addProperty(new PublicProperty("balcaovirtual.ws.documental.url"));
		super.addProperty(new PublicProperty("balcaovirtual.orgaos"));

		for (String s : SwaggerUtils.getProperty("balcaovirtual.orgaos", "").split(",")) {
			super.addProperty(new RestrictedProperty("balcaovirtual.mni." + s.toLowerCase() + ".url"));
			super.addProperty(new PublicProperty("balcaovirtual." + s.toLowerCase() + ".cota.tipo"));
		}

		super.addProperty(new PublicProperty("balcaovirtual.assijus.endpoint"));
		super.addProperty(new PublicProperty("balcaovirtual.assijus.system.expedientes"));
		super.addProperty(new PublicProperty("balcaovirtual.assijus.system.movimentos"));

		super.addProperty(new PrivateProperty("balcaovirtual.jwt.secret"));
		super.addProperty(new RestrictedProperty("balcaovirtual.upload.dir.final"));
		super.addProperty(new RestrictedProperty("balcaovirtual.upload.dir.temp"));

		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.remetente"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.host"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.host.alt"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.auth"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.auth.usuario"));
		super.addProperty(new PrivateProperty("balcaovirtual.smtp.auth.senha"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.porta"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.destinatario"));
		super.addProperty(new RestrictedProperty("balcaovirtual.smtp.assunto"));

		super.setAuthorizationToProperties(SwaggerUtils.getProperty("balcaovirtual.properties.secret", null));

		addDependency(new TestableDependency("database", "balcaovirtualds", false, 0, 10000) {
			@Override
			public String getUrl() {
				return SwaggerUtils.getProperty("balcaovirtual.datasource.name", "balcaovirtualds");
			}

			@Override
			public boolean test() throws Exception {
				try (Dao dao = new Dao()) {
					return dao.obtemData() != null;
				}
			}

			@Override
			public boolean isPartial() {
				return false;
			}
		});

	}

	@Override
	public int errorCode(Exception e) {
		return e.getMessage() == null || !e.getMessage().endsWith("(Alerta)") ? super.errorCode(e) : 400;
	}

	@Override
	public String getService() {
		return "sistemaprocessual";
	}

}
