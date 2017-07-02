import java.util.Arrays;

import ua.training.hashing.PasswordHashing;

public class HashTest {

	public static void main(String[] args) {
		//byte[] salt = PasswordHashing.generateRandomSalt();
		byte [] salt = new byte[] {112, 40, 104, 6, -38, 34, -127, 50, -46, 76, -89, 22, -123, -107, 8, -70};
		System.out.println("Salt: " + Arrays.toString(salt));
				
		String password = "librarian12";
		String hashedPass = PasswordHashing.generatePassHash256(password, salt);
		System.out.println("Hashed pass length: " + hashedPass.length());		
		System.out.println("Hashed pass: " + hashedPass);
		
		System.out.println(PasswordHashing.checkPassword(password, salt, hashedPass));

	}

}
