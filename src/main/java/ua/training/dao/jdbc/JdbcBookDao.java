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

import ua.training.dao.BookDao;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.exception.ServerException;

public class JdbcBookDao implements BookDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcBookDao.class);

	private static String GET_ALL_BOOKS_WITH_AUTHORS = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) ORDER BY title";

	private static String GET_BOOK_WITH_AUTHORS_AND_INSTANCES_BY_ID = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country," + " id_book_instance, status, inventory_number"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) LEFT JOIN book_instance USING (id_book)"
			+ " WHERE id_book=?";

	private static String CREATE_BOOK = "INSERT INTO book (isbn, title, publisher, availability) VALUES ( ?, ?, ?, ? )";
	private static String SAVE_BOOK_AUTHORS = "INSERT INTO book_author (id_book, id_author) VALUES ( ?, ?)";

	private static String UPDATE_BOOK = "UPDATE book SET isbn=?, title=?, publisher=?, availability=? WHERE id_book=?";

	private static String DELETE_BOOK = "DELETE FROM book WHERE id_book=?";

	private static String GET_BOOK_WITH_AUTHORS_AND_AVAILABLE_INSTANCES_BY_ID = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country," + " id_book_instance, status, inventory_number"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) JOIN book_instance USING (id_book)"
			+ " WHERE status='available' AND id_book=?";

	private static String SEARCH_BOOK_WITH_AUTHORS_BY_TITLE = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author)"
			+ " WHERE LOWER(title) LIKE CONCAT('%', LOWER(?), '%')";

	private static String SEARCH_BOOK_WITH_AUTHORS_BY_AUTHOR = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author)"
			+ " WHERE LOWER(author.surname) LIKE CONCAT('%', LOWER(?), '%') OR LOWER(author.name) LIKE CONCAT('%', LOWER(?), '%')"
			+ " OR CONCAT(LOWER(author.name), ' ', LOWER(author.surname)) LIKE CONCAT('%', LOWER(?), '%')";

	private static String SEARCH_BOOK_WITH_AUTHORS_BY_INSTANCE_ID = "SELECT book.id_book, isbn, title, publisher, availability,"
			+ " author.id_author, name, surname, country"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) JOIN book_instance USING (id_book)"
			+ " WHERE id_book_instance=?";

	// book table columns' names
	private static String BOOK_ID = "id_book";
	private static String BOOK_ISBN = "isbn";
	private static String BOOK_TITLE = "title";
	private static String BOOK_PUBLISHER = "publisher";
	private static String BOOK_AVAILABILITY = "availability";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcBookDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcBookDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Book> getAll() {
		List<Book> books = new ArrayList<>();

		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_ALL_BOOKS_WITH_AUTHORS)) {
			while (resultSet.next()) {
				books.add(extractBookWithAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return books;
	}

	@Override
	public Optional<Book> getById(Long id) {
		Optional<Book> book = Optional.empty();

		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_WITH_AUTHORS_AND_INSTANCES_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithInstancesAndAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return book;
	}

	@Override
	public void create(Book book) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, book.getIsbn());
			query.setString(2, book.getTitle());
			query.setString(3, book.getPublisher());
			query.setString(4, book.getAvailability().getValue());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();

			if (keys.next()) {
				book.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao create SQL exception: " + book.getIsbn(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void saveBookAuthors(Book book) {
		List<Author> authors = book.getAuthors();

		try (PreparedStatement query = connection.prepareStatement(SAVE_BOOK_AUTHORS)) {
			for (Author author : authors) {
				query.setLong(1, book.getId());
				query.setLong(2, author.getId());
				query.addBatch();
			}
			query.executeBatch();

		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao saveBookAuthors SQL exception: " + book.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Book book) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_BOOK)) {
			query.setString(1, book.getTitle());
			query.setString(2, book.getIsbn());
			query.setString(3, book.getPublisher());
			query.setString(4, book.getAvailability().getValue());
			query.setLong(5, book.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao update SQL exception: " + book.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_BOOK)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public Optional<Book> getBookWithAvailableInstances(Long id) {
		Optional<Book> book = Optional.empty();

		try (PreparedStatement query = connection
				.prepareStatement(GET_BOOK_WITH_AUTHORS_AND_AVAILABLE_INSTANCES_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithInstancesAndAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getBookWithAvailableInstances SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return book;
	}

	@Override
	public List<Book> searchBookWithAuthorsByTitle(String title) {
		List<Book> books = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_WITH_AUTHORS_BY_TITLE)) {
			query.setString(1, title);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookWithAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao searchByTsearchBookWithAuthorsByTitleitle SQL exception: " + title, e);
			throw new ServerException(e);
		}
		return books;
	}

	@Override
	public List<Book> searchBookWithAuthorsByAuthor(String author) {
		List<Book> books = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_WITH_AUTHORS_BY_AUTHOR)) {
			query.setString(1, author);
			query.setString(2, author);
			query.setString(3, author);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookWithAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao searchBookWithAuthorsByAuthor SQL exception: " + author, e);
			throw new ServerException(e);
		}
		return books;
	}

	@Override
	public Optional<Book> searchBookWithAuthorsByInstanceId(Long instanceId) {
		Optional<Book> book = Optional.empty();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_WITH_AUTHORS_BY_INSTANCE_ID)) {
			query.setLong(1, instanceId);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithAuthorsFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao searchByBookInstanceId SQL exception: " + instanceId, e);
			throw new ServerException(e);
		}
		return book;
	}

	protected static Book extractBookWithAuthorsFromResultSet(ResultSet resultSet) throws SQLException {
		Book book = extractBookFromResultSet(resultSet);
		book.addAuthor(JdbcAuthorDao.extractAuthorFromResultSet(resultSet));
		while (resultSet.next() && book.equals(extractBookFromResultSet(resultSet))) {
			book.addAuthor(JdbcAuthorDao.extractAuthorFromResultSet(resultSet));
		}
		resultSet.previous();
		return book;
	}

	protected static Book extractBookWithInstancesAndAuthorsFromResultSet(ResultSet resultSet) throws SQLException {
		Book book = extractBookFromResultSet(resultSet);
		Author author = JdbcAuthorDao.extractAuthorFromResultSet(resultSet);
		BookInstance bookInstance = JdbcBookInstanceDao.extractBookInstanceFromResultSet(resultSet);
		book.addAuthor(author);
		book.addBookInstance(bookInstance);
		while (resultSet.next()) {
			author = JdbcAuthorDao.extractAuthorFromResultSet(resultSet);
			bookInstance = JdbcBookInstanceDao.extractBookInstanceFromResultSet(resultSet);
			if (!book.getAuthors().contains(author)) {
				book.addAuthor(author);
			}
			if (!book.getBookInstances().contains(bookInstance)) {
				book.addBookInstance(bookInstance);
			}
		}
		return book;
	}

	protected static Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
		return new Book.Builder().setId(resultSet.getLong(BOOK_ID)).setIsbn(resultSet.getString(BOOK_ISBN))
				.setTitle(resultSet.getString(BOOK_TITLE)).setPublisher(resultSet.getString(BOOK_PUBLISHER))
				.setAvailability(Availability.forValue(resultSet.getString(BOOK_AVAILABILITY))).build();
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
