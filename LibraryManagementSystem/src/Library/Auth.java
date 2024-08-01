package Library;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Auth {
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		// generate a 16byte salt
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(salt);
		byte[] hashedPassword = digest.digest(password.getBytes());

		// password returned will be in the format salt$hashedPassword
		return byteToHexadecimal(salt) + "$" + byteToHexadecimal(hashedPassword);
	}

	public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(salt);
		byte[] hashedPassword = digest.digest(password.getBytes());

		// password returned will be in the format salt$hashedPassword
		return byteToHexadecimal(salt) + "$" + byteToHexadecimal(hashedPassword);
	}

	public static boolean verifyPassword(String dbPassword, String password) {
		try {
			String[] fields = dbPassword.split("\\$");
			if (fields.length != 2) {
				System.out.println("incorrect password format stored in database");
				return false;
			}

			byte[] salt = Auth.hexStringToByteArray(fields[0]);
			String hashedPassword = Auth.hashPassword(password, salt);
			return dbPassword.equals(hashedPassword);

		} catch (NoSuchAlgorithmException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private static String byteToHexadecimal(byte[] byteArray) {
		StringBuilder hex = new StringBuilder();

		// Iterating through each byte in the array
		for (byte i : byteArray) {
			hex.append(String.format("%02x", i));
		}

		return hex.toString();
	}

	private static byte[] hexStringToByteArray(String hexString) throws IllegalArgumentException {
		int length = hexString.length();
		if (length % 2 != 0) {
			throw new IllegalArgumentException("Hex string must have an even length");
		}

		byte[] data = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}
}
