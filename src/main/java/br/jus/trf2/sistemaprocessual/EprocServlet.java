package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;
import com.crivano.swaggerservlet.dependency.TestableDependency;
import com.crivano.swaggerservlet.property.PrivateProperty;
import com.crivano.swaggerservlet.property.RestrictedProperty;

public class EprocServlet extends SwaggerServlet {
	private static final long serialVersionUID = 1756711359239182178L;
	public static String servletContext = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		servletContext = config.getServletContext().getContextPath().replace("/", "");

		super.setAPI(ISistemaProcessual.class);

		super.setActionPackage("br.jus.trf2.sistemaprocessual");

		super.addProperty(new PrivateProperty("eprocapi.jwt.secret"));
		super.addProperty(new RestrictedProperty("eprocapi.upload.dir.final"));
		super.addProperty(new RestrictedProperty("eprocapi.upload.dir.temp"));

		super.addProperty(new RestrictedProperty("eprocapi.smtp.remetente"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.host"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.host.alt"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.auth"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.auth.usuario"));
		super.addProperty(new PrivateProperty("eprocapi.smtp.auth.senha"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.porta"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.destinatario"));
		super.addProperty(new RestrictedProperty("eprocapi.smtp.assunto"));

		super.setAuthorizationToProperties(SwaggerUtils.getProperty("eprocapi.properties.secret", null));

		addDependency(new TestableDependency("database", "balcaovirtualds", false, 0, 10000) {
			@Override
			public String getUrl() {
				return SwaggerUtils.getProperty("eprocapi.datasource.name", "balcaovirtualds");
			}

			@Override
			public boolean test() throws Exception {
				try (Connection conn = Utils.getConnection();
						PreparedStatement q = conn.prepareStatement("select sysdate()")) {
					ResultSet rs = q.executeQuery();
					while (rs.next()) {
						return true;
					}
				}
				return false;
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
