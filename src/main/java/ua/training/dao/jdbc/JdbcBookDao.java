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
import ua.training.entity.Status;
import ua.training.exception.ServerException;

public class JdbcBookDao implements BookDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcBookDao.class);

	// SQL queries
	private static String CREATE_BOOK = "INSERT INTO book (isbn, title, publisher, availability) VALUES ( ?, ?, ?, ? )";
	// private static String GET_ALL_BOOKS = "SELECT * FROM book ORDER BY
	// title";
	private static String GET_ALL_BOOKS = "SELECT book.id_book, isbn, title, publisher, availability, author.id_author, name, surname, country"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) ORDER BY title";

	// private static String GET_BOOK_BY_ID = "SELECT * FROM book WHERE
	// id_book=?";
	private static String GET_BOOK_BY_ID = "SELECT book.id_book, isbn, title, publisher, availability, author.id_author, name, surname, country,"
			+ " id_book_instance, status, inventory_number"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) LEFT JOIN book_instance USING (id_book)"
			+ " WHERE id_book=?";
	private static String GET_BOOK_WITH_AVAILABLE_INSTANCES = "SELECT book.id_book, isbn, title, publisher, availability, author.id_author, name, surname, country,"
			+ " id_book_instance, status, inventory_number"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) JOIN book_instance USING (id_book)"
			+ " WHERE status='available' AND id_book=?";
	private static String SAVE_BOOK_AUTHORS = "INSERT INTO book_author (id_book, id_author) VALUES ( ?, ?)";
	private static String UPDATE_BOOK = "UPDATE book SET isbn=?, title=?, publisher=?, availability=? WHERE id_book=?";
	private static String DELETE_BOOK = "DELETE FROM book WHERE id_book=?";
	private static String SEARCH_BOOK_BY_TITLE = "SELECT * FROM book WHERE LOWER(title) LIKE CONCAT('%', LOWER(?), '%')";
	private static String SEARCH_BOOK_BY_AUTHOR_SURNAME = "SELECT book.id_book, book.isbn, book.title, book.publisher, book.availability"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author)"
			+ " WHERE LOWER(author.surname) LIKE CONCAT('%', LOWER(?), '%') OR LOWER(author.name) LIKE CONCAT('%', LOWER(?), '%')"
			+ " OR CONCAT(LOWER(author.name), ' ', LOWER(author.surname)) LIKE CONCAT('%', LOWER(?), '%')";
	private static String SEARCH_BOOK_BY_INSTANCE_INVENTORY_NUMBER = "SELECT book.id_book, isbn, title, publisher, availability, author.id_author, name, surname, country,"
			+ " FROM book JOIN book_author USING (id_book) JOIN author USING (id_author) JOIN book_instance USING (id_book)"
			+ " WHERE inventory_number=?";

	// book fields
	private static String ID_BOOK = "id_book";
	private static String ISBN = "isbn";
	private static String TITLE = "title";
	private static String PUBLISHER = "publisher";
	private static String AVAILABILITY = "availability";

	// bookInsatnce fields
	private static String ID_BOOK_INSTANCE = "id_book_instance";
	private static String STATUS = "status";
	private static String INVENTORY_NUMBER = "inventory_number";

	// author fields
	private static String ID_AUTHOR = "id_author";
	private static String NAME = "name";
	private static String SURNAME = "surname";
	private static String COUNTRY = "country";

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

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL_BOOKS)) {
			while (resultSet.next()) {
				books.add(extractBookWithAuthors(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getAll SQL error", e);
			throw new ServerException(e);
		}
		return books;
	}

	private Book extractBookWithAuthors(ResultSet resultSet) throws SQLException {
		Book book = extractBookFromResultSet(resultSet);
		book.addAuthor(extractAuthorFromResultSet(resultSet));
		while (resultSet.next() && book.equals(extractBookFromResultSet(resultSet))) {
			book.addAuthor(extractAuthorFromResultSet(resultSet));
		}
		resultSet.previous();
		return book;
	}

	@Override
	public Optional<Book> getById(Long id) {
		Optional<Book> book = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithInstancesAndAuthors(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getById SQL error: " + id, e);
			throw new ServerException(e);
		}
		return book;
	}

	@Override
	public Optional<Book> getBookWithAvailableInstances(Long id) {
		Optional<Book> book = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_WITH_AVAILABLE_INSTANCES)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithInstancesAndAuthors(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getById SQL error: " + id, e);
			throw new ServerException(e);
		}
		return book;
	}

	private Book extractBookWithInstancesAndAuthors(ResultSet resultSet) throws SQLException {
		Book book = extractBookFromResultSet(resultSet);
		Author author = extractAuthorFromResultSet(resultSet);
		BookInstance bookInstance = extractBookInstanceFromResultSet(resultSet);
		book.addAuthor(author);
		book.addBookInstance(bookInstance);
		while (resultSet.next()) {
			author = extractAuthorFromResultSet(resultSet);
			bookInstance = extractBookInstanceFromResultSet(resultSet);
			if (!book.getAuthors().contains(author)) {
				book.addAuthor(author);
			}
			if (!book.getBookInstances().contains(bookInstance)) {
				book.addBookInstance(bookInstance);
			}
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
			LOGGER.error("JdbcBookDao create SQL error: " + book.toString(), e);
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
			LOGGER.error("JdbcBookDao update SQL error: " + book.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_BOOK)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao delete SQL error: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<Book> searchByTitle(String title) {
		List<Book> books = new ArrayList<>();
		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_BY_TITLE)) {
			query.setString(1, title);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao searchByTitle SQL error: " + title, e);
			throw new ServerException(e);
		}
		return books;
	}

	@Override
	public List<Book> searchByAuthor(String authorSurname) {
		List<Book> books = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_BY_AUTHOR_SURNAME)) {
			query.setString(1, authorSurname);
			query.setString(2, authorSurname);
			query.setString(3, authorSurname);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao searchByAuthorSurname SQL error: " + authorSurname, e);
			throw new ServerException(e);
		}
		return books;
	}
	
	@Override
	public Optional<Book> searchByBookInstanceInventoryNumber(String instanceInventoryNumber) {
		Optional<Book> book = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SEARCH_BOOK_BY_INSTANCE_INVENTORY_NUMBER)) {
			query.setString(1, instanceInventoryNumber);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookWithAuthors(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookDao getBookByInstanceInventoryNumber SQL error: " + instanceInventoryNumber, e);
			throw new ServerException(e);
		}
		return book;
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
			LOGGER.error("JdbcBookDao saveBookAuthors SQL error: " + book.getId(), e);
			throw new ServerException(e);
		}
	}

	private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
		return new Book.Builder().setId(resultSet.getLong(ID_BOOK)).setIsbn(resultSet.getString(ISBN))
				.setTitle(resultSet.getString(TITLE)).setPublisher(resultSet.getString(PUBLISHER))
				.setAvailability(Availability.forValue(resultSet.getString(AVAILABILITY))).build();
	}

	public Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
		return new Author.Builder().setId(resultSet.getLong(ID_AUTHOR)).setName(resultSet.getString(NAME))
				.setSurname(resultSet.getString(SURNAME)).setCountry(resultSet.getString(COUNTRY)).build();

	}

	private BookInstance extractBookInstanceFromResultSet(ResultSet resultSet) throws SQLException {
		return new BookInstance.Builder().setId(resultSet.getLong(ID_BOOK_INSTANCE))
				.setStatus(checkResultSetStatusValue(resultSet))
				.setInventoryNumber(resultSet.getString(INVENTORY_NUMBER))
				.setBook(new Book.Builder().setId(resultSet.getLong(ID_BOOK)).build()).build();
	}
	
	private Status checkResultSetStatusValue(ResultSet resultSet) throws SQLException{
		return (resultSet.getString(STATUS) == null)? null: Status.forValue(resultSet.getString(STATUS));
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
