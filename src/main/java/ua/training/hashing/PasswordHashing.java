package ua.training.hashing;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public final class PasswordHashing {

	private PasswordHashing() {

	}

	public static String generatePassHash256(String passwardToHash, byte[] salt) {
		return DigestUtils.sha256Hex(DigestUtils.sha256Hex(salt) + passwardToHash);
	}

	public static byte[] generateRandomSalt() {
		Random sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt;

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean checkPassword(String passToCheck, byte[] salt, String hashedPassword) {
		return hashedPassword.equals(generatePassHash256(passToCheck, salt));
	}
}
