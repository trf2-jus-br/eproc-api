package br.jus.trf2.sistemaprocessual;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.crivano.swaggerservlet.SwaggerUtils;

public class Utils {
	public static Connection getConnection() throws Exception {
		String dsName = Utils.getProperty("datasource.name", null);
		if (dsName != null) {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:");
			DataSource ds = (DataSource) envContext.lookup(dsName);
			Connection connection = ds.getConnection();
			if (connection == null)
				throw new Exception("Can't open connection to database.");
			return connection;
		} else {
			Connection connection = null;

			Class.forName("com.mysql.jdbc.Driver");

			String dbURL = Utils.getProperty("datasource.url", null);
			String username = Utils.getProperty("datasource.username", null);
			;
			String password = Utils.getProperty("datasource.password", null);
			;
			connection = DriverManager.getConnection(dbURL, username, password);
			if (connection == null)
				throw new Exception("Can't open connection to database.");
			return connection;
		}
	}

	public static String getProperty(String propertyName, String defaultValue) {
		return SwaggerUtils.getProperty(EprocServlet.servletContext + "." + propertyName, defaultValue);
	}

	public static String getSQL(String filename) {
		try (Scanner scanner = new Scanner(EprocServlet.class.getResourceAsStream(filename + ".sql"), "UTF-8")) {
			String text = scanner.useDelimiter("\\A").next();
			return text;
		}
	}

	public static byte[] calcSha256(byte[] content) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(content);
		byte[] output = md.digest();
		return output;
	}

	/**
	 * Transoforma array de bytes em String
	 * 
	 * @param buf
	 * @return
	 */
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

}