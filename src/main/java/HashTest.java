import java.util.Arrays;

import ua.training.hashing.PasswordHashing;

public class HashTest {

	public static void main(String[] args) {
		byte[] salt = PasswordHashing.generateRandomSalt();
		// [47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33]
		System.out.println("Salt: " + Arrays.toString(salt));
		//101de87818bfebfdcb92f18d2e5e43b69e4f70783e0cd66c481bc75d0d688582
		String password = "library7";
		String hashedPass = PasswordHashing.generatePassHash256(password, salt);
		System.out.println("Hashed pass length: " + hashedPass.length());		
		System.out.println("Hashed pass: " + hashedPass);
		
		System.out.println(PasswordHashing.checkPassword(password, salt, hashedPass));

	}

}
