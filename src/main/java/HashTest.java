import java.util.Arrays;

import ua.training.hashing.PasswordHashing;

public class HashTest {

	public static void main(String[] args) {
		//byte[] salt = PasswordHashing.getInstance().generateRandomSalt();
		byte[] salt = new byte[]{124, 57, 68, 4, 83, -45, 127, 17, -52, -39, -73, -29, -40, 76, 123, 46};
		
		System.out.println("Salt: " + Arrays.toString(salt));
	
		String password = "sofiya14";
		String hashedPass = PasswordHashing.getInstance().generatePassHash256(password, salt);
		System.out.println("Hashed pass length: " + hashedPass.length());		
		System.out.println("Hashed pass: " + hashedPass);
		
		System.out.println(PasswordHashing.getInstance().checkPassword(password, salt, hashedPass));

	}

}
