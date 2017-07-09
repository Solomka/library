package ua.training.model.dao.jdbc;

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

import ua.training.exception.ServerException;
import ua.training.model.dao.AuthorDao;
import ua.training.model.entity.Author;
import ua.training.model.entity.Book;

public class JdbcAuthorDao implements AuthorDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcAuthorDao.class);

	// sql
	private static String CRAETE_AUTHOR = "INSERT INTO author (name, surname, country) VALUES (?, ?, ?)";
	private static String GET_ALL_AUTHORS = "SELECT * FROM author";
	private static String GET_BOOK_AUTHORS = "SELECT id_author, name, surname, country"
			+ " FROM author JOIN book_author USING (id_author)" + " WHERE id_book=?";

	// fields
	private static String ID_AUTHOR = "id_author";
	private static String NAME = "name";
	private static String SURNAME = "surname";
	private static String COUNTRY = "country";

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
			LOGGER.error("JdbcAuthorkDao getAll SQL error", e);
			throw new ServerException(e);
		}
		return authors;
		
	}

	@Override
	public Optional<Author> getById(Long id) {
		// TODO Auto-generated method stub
		return null;
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
			LOGGER.error("JdbcAuthorkDao create SQL error: " + author.toString(), e);
			throw new ServerException(e);
		}

	}

	@Override
	public void update(Author e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Author> getBookAuthors(Long bookId) {
		List<Author> authors = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_AUTHORS)) {
			query.setLong(1, bookId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				authors.add(extractAuthorFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcAuthorDao getBookAuthors SQL error: " + bookId, e);
			throw new ServerException(e);
		}
		return authors;
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

	public Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
		return new Author.Builder().setId(resultSet.getLong(ID_AUTHOR)).setName(resultSet.getString(NAME))
				.setSurname(resultSet.getString(SURNAME)).setCountry(resultSet.getString(COUNTRY)).build();

	}
}
