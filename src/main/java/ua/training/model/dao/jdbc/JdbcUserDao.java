package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.exception.ServerException;
import ua.training.locale.Message;
import ua.training.locale.MessageLocale;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.Librarian;
import ua.training.model.entity.Reader;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

public class JdbcUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

	private static final String SELECT_USER_BY_LOGIN = "select * from users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian "
			+ "union "
			+ "select * from users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader "
			+ "WHERE users.login=?";

	private static String ID = "id_user";
	private static String LOGIN = "login";
	private static String PASSWORD = "password";
	private static String ROLE = "role";
	private static String LIBRARIAN_NAME = "librarian.name";
	private static String LIBRARIAN_SURNAME = "librarian.surname";
	private static String LIBRARIAN_PATRONYMIC = "librarian.patronymic";
	private static String LIBRARIAN_EMAIL = "librarian.email";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcUserDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcUserDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(User e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * TODO: do normally!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	@SuppressWarnings("unchecked")
	@Override
	public<T extends User>  Optional<T> getUserByLoginTest(String email) {
	
		return Optional.of((T) new Librarian.Builder().setId(new Long("1")).setLogin("librarian@gmail.com").setPassword("8c935809a1effb885c8f453cda23bf171aba22f3b2cc4405e44b122700943fcb")
				.setRole(Role.LIBRARIAN).setEmail("librarian@gmail.com").setName("A").setSurname("B")
				.setPatronymic("C").build());
		/*try {
			throw  new Exception();
		} catch (Exception e) {
			throw new ServerException(e);
		}*/
		
	}
	@Override
	public <T extends User> Optional<T> getUserByLogin(String login) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
			query.setString(1, login);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			LOGGER.error("JdbcUserDao getUserByLogin SQL error: " + login, e);
			throw new ServerException(e);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	private <T extends User> T extractUserFromResultSet(ResultSet resultSet) throws SQLException {

		if (resultSet.getString(ROLE).equals(Role.LIBRARIAN.getValue())) {
			return (T) extractLibrarianFromResultSet(resultSet);
		} else {
			return (T) extractReaderFromResultSet(resultSet);
		}
	}

	private Reader extractReaderFromResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	private Librarian extractLibrarianFromResultSet(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return new Librarian.Builder().setId(resultSet.getLong(ID)).setLogin(resultSet.getString(LOGIN))
				.setPassword(resultSet.getString(PASSWORD)).setRole(Role.forValue(resultSet.getString(ROLE))).build();
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
