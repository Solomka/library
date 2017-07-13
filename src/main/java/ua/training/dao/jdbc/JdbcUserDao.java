package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.UserDao;
import ua.training.entity.Librarian;
import ua.training.entity.Reader;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.exception.ServerException;

public class JdbcUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

	private static String GET_ALL_USERS_BY_ROLE = "SELECT *"
			+ " FROM (SELECT id_user, email, password, role, salt, reader.name,"
			+ " reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian"
			+ " UNION" + " SELECT id_user, email, password, role, salt,"
			+ " reader.name, reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader)"
			+ " AS tb" + " WHERE role=?";
	private static String GET_USER_BY_ID = "SELECT *"
			+ " FROM (SELECT id_user, email, password, role, salt, reader.name,"
			+ " reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian"
			+ " UNION" + " SELECT id_user, email, password, role, salt,"
			+ " reader.name, reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader)"
			+ " AS tb" + " WHERE id_user=?";
	private static String GET_USER_BY_EMAIL = "SELECT *"
			+ " FROM (SELECT id_user, email, password, role, salt, reader.name,"
			+ " reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number,"
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian"
			+ " UNION" + " SELECT id_user, email, password, role, salt,"
			+ " reader.name, reader.surname, reader.patronymic, reader.phone, reader.address, reader.reader_card_number, "
			+ " librarian.name AS l_name, librarian.surname AS l_surname, librarian.patronymic AS l_patronymic"
			+ " FROM users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader)"
			+ " AS tb" + " WHERE email=?";
	private static String CREATE_USER = "INSERT INTO users (email, password, role, salt) VALUES (?, ?, ?, ?)";
	private static String CREATE_READER = "INSERT INTO reader (id_reader, name, surname, patronymic, phone, address, reader_card_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static String CREATE_LIBRARIAN = "INSERT INTO librarian (id_librarian, name, surname, patronymic) VALUES (?, ?, ?, ?)";
	private static String UPDATE_READER = "UPDATE users JOIN reader ON users.id_user = reader.id_reader"
			+ " SET users.email=?, users.password=?, users.salt=?, reader.name=?, reader.surname=?, reader.patronymic=?, reader.phone=?, reader.address=?, reader.reader_card_number=?"
			+ " WHERE id_user=? ";
	private static String UPDATE_LIBRARIAN = "UPDATE users JOIN librarian ON users.is_user = librarian.id_librarian"
			+ " SET users.email=?, users.password=?, users.salt=?, librarian.name=?, librarian.surname=?, librarian.patronymic=?"
			+ " WHERE id_user=?";
	private static String DELETE_USER = "DELETE FROM users WHERE id_user=?";

	// librarian fields
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
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends User> List<T> getAllUsers(Role role) {
		List<T> users = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_ALL_USERS_BY_ROLE)) {
			query.setString(1, role.getValue());
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				users.add(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getAllUsers SQL exception", e);
			throw new ServerException(e);
		}
		return users;
	}

	@Override
	public Optional<User> getById(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends User> Optional<T> getUserById(Long id) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_USER_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			LOGGER.error("JdbcUserDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return user;
	}

	@Override
	public <T extends User> Optional<T> getUserByEmail(String email) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_USER_BY_EMAIL)) {
			query.setString(1, email);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			LOGGER.error("JdbcUserDao getUserByEmail SQL exception: " + email, e);
			throw new ServerException(e);
		}
		return user;
	}

	@Override
	public void create(User user) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, user.getEmail());
			query.setString(2, user.getPassword());
			query.setString(3, user.getRole().getValue());
			query.setBytes(4, user.getSalt());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				user.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao create SQL exception: " + user.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void createReader(Reader reader) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_READER)) {
			query.setLong(1, reader.getId());
			query.setString(2, reader.getName());
			query.setString(3, reader.getSurname());
			query.setString(4, reader.getPatronymic());
			query.setString(5, reader.getPhone());
			query.setString(6, reader.getAddress());
			query.setString(7, reader.getReaderCardNumber());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao create reader SQL exception: " + reader.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void createLibrarian(Librarian librarian) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_LIBRARIAN)) {
			query.setLong(1, librarian.getId());
			query.setString(2, librarian.getName());
			query.setString(3, librarian.getSurname());
			query.setString(4, librarian.getPatronymic());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao create librarian SQL exception: " + librarian.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(User user) {
		if (isLibrarian(user)) {
			Librarian librarian = (Librarian) user;
			try (PreparedStatement query = connection.prepareStatement(UPDATE_LIBRARIAN)) {
				query.setString(1, librarian.getEmail());
				query.setString(2, librarian.getPassword());
				query.setBytes(3, librarian.getSalt());
				query.setString(4, librarian.getName());
				query.setString(5, librarian.getSurname());
				query.setString(6, librarian.getPatronymic());
				query.setLong(7, librarian.getId());
				query.executeUpdate();

			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao librarian update SQL exception: " + librarian.getId(), e);
				throw new ServerException(e);
			}

		} else {
			Reader reader = (Reader) user;
			try (PreparedStatement query = connection.prepareStatement(UPDATE_READER)) {
				query.setString(1, reader.getEmail());
				query.setString(2, reader.getPassword());
				query.setBytes(3, reader.getSalt());
				query.setString(4, reader.getName());
				query.setString(5, reader.getSurname());
				query.setString(6, reader.getPatronymic());
				query.setString(7, reader.getPhone());
				query.setString(8, reader.getAddress());
				query.setString(9, reader.getReaderCardNumber());
				query.setLong(10, reader.getId());
				query.executeUpdate();

			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao reader update SQL exception: " + reader.getId(), e);
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
			LOGGER.error("JdbcBookDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}

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
		return new Reader.Builder().setId(resultSet.getLong(Column.USER_ID))
				.setEmail(resultSet.getString(Column.USER_EMAIL)).setPassword(resultSet.getString(Column.USER_PASSWORD))
				.setRole(Role.forValue(resultSet.getString(Column.USER_ROLE)))
				.setSalt(resultSet.getBytes(Column.USER_SALT)).setName(resultSet.getString(Column.READER_NAME))
				.setSurname(resultSet.getString(Column.READER_SURNAME))
				.setPatronymic(resultSet.getString(Column.READER_PATRONYMIC))
				.setPhone(resultSet.getString(Column.READER_PHONE))
				.setAddress(resultSet.getString(Column.READER_ADDRESS))
				.setReaderCardNumber(resultSet.getString(Column.READER_READER_CARD_NUMBER)).build();
	}

	private Librarian extractLibrarianFromResultSet(ResultSet resultSet) throws SQLException {
		return new Librarian.Builder().setId(resultSet.getLong(Column.USER_ID))
				.setEmail(resultSet.getString(Column.USER_EMAIL)).setPassword(resultSet.getString(Column.USER_PASSWORD))
				.setRole(Role.forValue(resultSet.getString(Column.USER_ROLE)))
				.setSalt(resultSet.getBytes(Column.USER_SALT)).setName(resultSet.getString(LIBRARIAN_NAME))
				.setSurname(resultSet.getString(LIBRARIAN_SURNAME))
				.setPatronymic(resultSet.getString(LIBRARIAN_PATRONYMIC)).build();
	}

	private boolean isLibrarian(ResultSet resultSet) throws SQLException {
		return resultSet.getString(Column.USER_ROLE).equals(Role.LIBRARIAN.getValue());
	}

	private boolean isLibrarian(User user) {
		return user.getRole().equals(Role.LIBRARIAN.getValue());
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
