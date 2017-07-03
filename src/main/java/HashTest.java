import java.util.Arrays;

import ua.training.hashing.PasswordHashing;

public class HashTest {

	public static void main(String[] args) {
		byte[] salt = PasswordHashing.generateRandomSalt();
		
		System.out.println("Salt: " + Arrays.toString(salt));
	
		String password = "sofiya14";
		String hashedPass = PasswordHashing.generatePassHash256(password, salt);
		System.out.println("Hashed pass length: " + hashedPass.length());		
		System.out.println("Hashed pass: " + hashedPass);
		
		System.out.println(PasswordHashing.checkPassword(password, salt, hashedPass));

	}

}
