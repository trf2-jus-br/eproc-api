package br.jus.trf2.sistemaprocessual;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.crivano.swaggerservlet.SwaggerServlet;

public class Utils {
	public static Connection getConnection() throws Exception {
		String dsName = SwaggerServlet.getProperty("datasource.name");
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

			String dbURL = SwaggerServlet.getProperty("datasource.url");
			String username = SwaggerServlet.getProperty("datasource.username");
			;
			String password = SwaggerServlet.getProperty("datasource.password");
			;
			connection = DriverManager.getConnection(dbURL, username, password);
			if (connection == null)
				throw new Exception("Can't open connection to database.");
			return connection;
		}
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

	private static final DateTimeFormatter dtfBRHHMM = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");

	public static String formatarDataHoraMinuto(Date d) {
		DateTime dt = new DateTime(d.getTime());
		return dt.toString(dtfBRHHMM);
	}

}