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

	private static final String SELECT_USER_BY_EMAIL = "SELECT *"
			+ " FROM (SELECT id_user, email, password, role, salt, reader.name,"
			+ " reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian"
			+ " UNION"
			+ " SELECT id_user, email, password, role, salt,"
			+ " reader.name, reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number, "
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader)"
			+ " AS tb"
			+ " WHERE email=?";

	//user fields
	private static String ID = "id_user";
	private static String EMAIL = "email";
	private static String PASSWORD = "password";
	private static String ROLE = "role";
	private static String SALT = "salt";
	
	//reader fields
	private static String READER_NAME = "name";
	private static String READER_SURNAME = "surname";
	private static String READER_PATRONYMIC = "patronymic";
	private static String READER_PHONE = "phone";
	private static String READER_ADDRESS = "address";
	private static String READER_READER_CARD_NUMBER = "reader_card_number";
	
	//librarian fields
	private static String LIBRARIAN_NAME = "l_name";
	private static String LIBRARIAN_SURNAME = "l_surname";
	private static String LIBRARIAN_PATRONYMIC = "l_patronymic";


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

	
	@Override
	public <T extends User> Optional<T> getUserByEmail(String email) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
			query.setString(1, email);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			LOGGER.error("JdbcUserDao getUserByEmail SQL error: " + email, e);
			throw new ServerException(e);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	private <T extends User> T extractUserFromResultSet(ResultSet resultSet) throws SQLException {
		if (isLibrarian(resultSet)) {
			return (T) extractLibrarianFromResultSet(resultSet);
		} else {
			return (T) extractReaderFromResultSet(resultSet);
		}
	}

	private Reader extractReaderFromResultSet(ResultSet resultSet) throws SQLException {
		return new Reader.Builder().setId(resultSet.getLong(ID)).setEmail(resultSet.getString(EMAIL))
				.setPassword(resultSet.getString(PASSWORD)).setRole(Role.forValue(resultSet.getString(ROLE))).setSalt(resultSet.getBytes(SALT))
				.setName(resultSet.getString(READER_NAME)).setSurname(resultSet.getString(READER_SURNAME))
				.setPatronymic(resultSet.getString(READER_PATRONYMIC)).setPhone(resultSet.getString(READER_PHONE))
				.setAddress(resultSet.getString(READER_ADDRESS)).setReaderCardNumber(resultSet.getString(READER_READER_CARD_NUMBER)).build();
	}

	private Librarian extractLibrarianFromResultSet(ResultSet resultSet) throws SQLException {
		return new Librarian.Builder().setId(resultSet.getLong(ID)).setEmail(resultSet.getString(EMAIL))
				.setPassword(resultSet.getString(PASSWORD)).setRole(Role.forValue(resultSet.getString(ROLE))).setSalt(resultSet.getBytes(SALT))
				.setName(resultSet.getString(LIBRARIAN_NAME)).setSurname(resultSet.getString(LIBRARIAN_SURNAME))
				.setPatronymic(resultSet.getString(LIBRARIAN_PATRONYMIC)).build();
	}
	
	private boolean isLibrarian(ResultSet resultSet) throws SQLException{
		return resultSet.getString(ROLE).equals(Role.LIBRARIAN.getValue());		
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}

}
