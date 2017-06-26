package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.model.dao.UserDao;
import ua.training.model.entity.Librarian;
import ua.training.model.entity.Reader;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

public class JdbcUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

	private static final String SELECT_USER_BY_LOGIN = "select * from users JOIN reader ON users.id_user = reader.id_reader LEFT JOIN librarian ON users.id_user = librarian.id_librarian "
			+ "union"
			+ "select * from users JOIN librarian ON users.id_user = librarian.id_librarian LEFT JOIN reader ON users.id_user = reader.id_reader";

	private static final String ROLE = "user.role";

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
	public <T extends User> Optional<T> getUserByLogin(String login) {
		Optional<T> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
			query.setString(1, login);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

	private Librarian extractLibrarianFromResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
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
