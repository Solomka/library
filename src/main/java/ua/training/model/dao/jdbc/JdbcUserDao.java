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
	
	private static String SELECT_USER_BY_ID = "SELECT *"
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
			+ " WHERE id_user=?";

	private static String SELECT_USER_BY_EMAIL = "SELECT *"
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
	
	private static String UPDATE_READER ="UPDATE users JOIN reader ON users.id_user = reader.id_reader"
			+ " SET users.email=?, users.password=?, reader.name=?, reader.surname=?, reader.patronymic=?, reader.phone=?, reader.address=?, reader.reader_card_number?"
			+ " WHERE id_user=? ";
	private static  String UPDATE_LIBRARIAN = "UPDATE users JOIN librarian ON users.is_user = librarian.id_librarian"
			+ " SET users.email=?, users.password=?, librarian.name=?, librarian.surname=?, librarian.patronymic=?"
			+ " WHERE id_user=?";
	
	private static String DELETE_USER = "DELETE FROM users WHERE id_user=?";

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
	public Optional<User> getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void create(User e) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends User> Optional<T> getUserById(Long id) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			LOGGER.error("JdbcUserDao getById SQL error: " + id, e);
			throw new ServerException(e);
		}
		return user;
	}	

	@Override
	public void update(User user) {
		if(user.getRole().equals(Role.LIBRARIAN)){
			Librarian librarian = (Librarian) user;
			try (PreparedStatement query = connection.prepareStatement(UPDATE_READER)) {
				query.setString(1, librarian.getEmail());
				query.setString(2, librarian.getPassword());
				query.setString(3, librarian.getName());
				query.setString(5, librarian.getSurname());
				query.setString(6, librarian.getPatronymic());
				query.setLong(7, librarian.getId());
				query.executeUpdate();

			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao update SQL error: " + librarian.toString(), e);
				throw new ServerException(e);
			}
			
		}else{
			Reader reader = (Reader) user;
			try (PreparedStatement query = connection.prepareStatement(UPDATE_LIBRARIAN)) {
				query.setString(1, reader.getEmail());
				query.setString(2, reader.getPassword());
				query.setString(3, reader.getName());
				query.setString(4, reader.getSurname());
				query.setString(5, reader.getPatronymic());
				query.setString(6, reader.getPhone());
				query.setString(7, reader.getAddress());
				query.setString(8, reader.getReaderCardNumber());
				query.setLong(9, reader.getId());
				query.executeUpdate();

			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao update SQL error: " + reader.toString(), e);
				throw new ServerException(e);
			}
			
		}

	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_USER)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao delete SQL error: " + id, e);
			throw new ServerException(e);
		}

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
