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

import ua.training.dao.AuthorDao;
import ua.training.entity.Author;
import ua.training.exception.ServerException;

public class JdbcAuthorDao implements AuthorDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcAuthorDao.class);

	private static String GET_ALL_AUTHORS = "SELECT id_author, name, surname, country FROM author";
	private static String GET_AUTHOR_BY_ID = "SELECT id_author, name, surname, country FROM author WHERE id_author=?";
	private static String CRAETE_AUTHOR = "INSERT INTO author (name, surname, country) VALUES (?, ?, ?)";
	private static String UPDATE_AUTHOR = "UPDATE author SET name=?, surname=?, country=? WHERE id_author=?";
	private static String DELETE_AUTHOR = "DELETE FROM author WHERE id_author=?";

	// author table columns' names
	private static String AUTHOR_ID = "id_author";
	private static String AUTHOR_NAME = "name";
	private static String AUTHOR_SURNAME = "surname";
	private static String AUTHOR_COUNTRY = "country";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcAuthorDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcAuthorDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Author> getAll() {

		List<Author> authors = new ArrayList<>();
		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_ALL_AUTHORS)) {
			while (resultSet.next()) {
				authors.add(extractAuthorFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return authors;
	}

	@Override
	public Optional<Author> getById(Long id) {
		Optional<Author> author = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_AUTHOR_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				author = Optional.of(extractAuthorFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return author;
	}

	@Override
	public void create(Author author) {
		try (PreparedStatement query = connection.prepareStatement(CRAETE_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, author.getName());
			query.setString(2, author.getSurname());
			query.setString(3, author.getCountry());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				author.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Author author) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_AUTHOR)) {
			query.setString(1, author.getName());
			query.setString(2, author.getSurname());
			query.setString(3, author.getCountry());
			query.setLong(4, author.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao update SQL exception: " + author.getId(), e);
			throw new ServerException(e);
		}

	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_AUTHOR)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	protected static Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
		return new Author.Builder().setId(resultSet.getLong(AUTHOR_ID)).setName(resultSet.getString(AUTHOR_NAME))
				.setSurname(resultSet.getString(AUTHOR_SURNAME)).setCountry(resultSet.getString(AUTHOR_COUNTRY))
				.build();
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcBookDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}
}
