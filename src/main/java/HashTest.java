import java.util.Arrays;

import ua.training.hashing.PasswordHashing;

public class HashTest {

	public static void main(String[] args) {
		//byte[] salt = PasswordHashing.getInstance().generateRandomSalt();
		byte[] salt = new byte[]{47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33};
		
		System.out.println("Salt: " + Arrays.toString(salt));
	
		String password = "library7";
		String hashedPass = PasswordHashing.getInstance().generatePassHash256(password, salt);
		System.out.println("Hashed pass length: " + hashedPass.length());		
		System.out.println("Hashed pass: " + hashedPass);
		
		System.out.println(PasswordHashing.getInstance().checkPassword(password, salt, hashedPass));

	}

}
