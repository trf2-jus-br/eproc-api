package br.jus.trf2.sistemaprocessual;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Utils {
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
}