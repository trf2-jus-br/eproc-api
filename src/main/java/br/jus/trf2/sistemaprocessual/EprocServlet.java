package br.jus.trf2.sistemaprocessual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.dependency.TestableDependency;

public class EprocServlet extends SwaggerServlet {
	private static final long serialVersionUID = 1756711359239182178L;

	@Override
	public void initialize(ServletConfig config) throws ServletException {
		setAPI(ISistemaProcessual.class);

		setActionPackage("br.jus.trf2.sistemaprocessual");

		addRestrictedProperty("datasource.url", null);
		if (getProperty("datasource.url") != null) {
			addRestrictedProperty("datasource.username");
			addRestrictedProperty("datasource.password");
			addRestrictedProperty("datasource.name", null);
		} else {
			addRestrictedProperty("datasource.username", null);
			addRestrictedProperty("datasource.password", null);
			addRestrictedProperty("datasource.name", "balcaovirtualds");
		}

		addDependency(new TestableDependency("database", "balcaovirtualds", false, 0, 10000) {
			@Override
			public String getUrl() {
				return getProperty("datasource.name");
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
