package ua.training.hashing;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.exception.ServerException;

/**
 * Class that is responsible for password hashing execution
 * 
 * @author Solomka
 *
 */
public final class PasswordHashing {

	private static final Logger LOGGER = LogManager.getLogger(PasswordHashing.class);

	private static final String HASHING_SALT_FILE = "/hashing.properties";
	private static final String WEBSITE_SALT_KEY = "website.solt";
	private static String WEBSITE_SALT;

	private PasswordHashing() {
		try {
			InputStream inputStream = PasswordHashing.class.getResourceAsStream(HASHING_SALT_FILE);
			Properties dbProps = new Properties();
			dbProps.load(inputStream);
			WEBSITE_SALT = dbProps.getProperty(WEBSITE_SALT_KEY);
		} catch (IOException e) {
			LOGGER.error("Can't load local salt form properties file", e);
			throw new ServerException(e);
		}
	}

	private static class Holder {
		static final PasswordHashing INSTANCE = new PasswordHashing();
	}

	public static PasswordHashing getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Generates password hash using salt unique for each password that is
	 * stored in db and application salt that is stored in hashing.properties
	 * file by means of sha256Hex algorithm
	 * 
	 * @param passwardToHash
	 *            password to hash
	 * @param salt
	 *            salt using for pass hashing
	 * @return hashing password
	 */
	public String generatePassHash256(String passwardToHash, byte[] salt) {
		return DigestUtils.sha256Hex(DigestUtils.sha256Hex(passwardToHash) + DigestUtils.sha256Hex(salt)
				+ DigestUtils.sha256Hex(WEBSITE_SALT));
	}

	/**
	 * Generates random salt that is unique for each password and is stored with
	 * password hash in db
	 * 
	 * @return random salt
	 */
	public byte[] generateRandomSalt() {
		Random sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt;

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SecureRandom random salt generation error", e);
			throw new ServerException(e);
		}
	}

	public boolean checkPassword(String passToCheck, byte[] salt, String hashedPassword) {
		return hashedPassword.equals(generatePassHash256(passToCheck, salt));
	}
}
