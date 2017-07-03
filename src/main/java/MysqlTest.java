import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import ua.training.model.entity.Librarian;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

public class MysqlTest {

	public static void main(String[] args) {
		
		 byte[] salt = new byte[]{124, 57, 68, 4, 83, -45, 127, 17, -52, -39, -73, -29, -40, 76, 123, 46};
		
		Librarian librarian = new Librarian.Builder().setEmail("anna@gmail.com").setPassword("101de87818bfebfdcb92f18d2e5e43b69e4f70783e0cd66c481bc75d0d688582")
				.setRole(Role.LIBRARIAN).setEmail("anna@gmail.com").setName("Анна").setSurname("Єршак")
				.setPatronymic("Миколаївна").build();
		//User user = new User();
		
		try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    Connection conn = null;
    try {
        conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/training_library?characterEncoding=UTF-8", "Solomka", "solomka77");
       
        String query = "UPDATE users SET salt=? WHERE id_user=?";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
        pstmt.setBytes(1, salt);
        pstmt.setLong(2, new Long("2"));
        pstmt.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) {}
        }
    }
    System.out.println("done :)");
  //  System.out.println("Salt: " + Arrays.toString(user.getSalt()));
		
		/*try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/training_library?characterEncoding=UTF-8", "Solomka", "solomka77");
           
            String query = "SELECT salt FROM users where id_user=? ";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
            pstmt.setLong(1, 6);
            ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				user.setSalt(resultSet.getBytes("salt"));
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
        System.out.println("done :)");
        System.out.println("Salt: " + Arrays.toString(user.getSalt()));*/
        
		/*try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/training_library?characterEncoding=UTF-8", "Solomka", "solomka77");
            byte[] salt = new byte[]{47, 105, -75, 51, -9, 95, -109, 107, 58, 68, 39, -56, -18, -31, -85, 33};
            String query = "INSERT INTO users (login, password, role, salt) VALUES (?,?,?,?)";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
            pstmt.setString(1, librarian.getEmail());
            pstmt.setString(2, librarian.getPassword() );
            pstmt.setString(3, librarian.getRole().getValue());
            pstmt.setBytes(4, salt);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
        System.out.println("done :)");*/

	}

}
