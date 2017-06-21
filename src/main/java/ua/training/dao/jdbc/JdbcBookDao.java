package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.training.dao.BookDao;
import ua.training.entity.Availability;
import ua.training.entity.Book;

public class JdbcBookDao implements BookDao {

	// SQL queries
	private static final String SELECT_ALL_FROM_BOOK = "SELECT * FROM book ORDER BY title";
	private static final String SELECT_BOOK_BY_ID = "SELECT * FROM book WHERE id_book=?";
	private static final String CREATE_NEW_BOOK = "INSERT INTO book (isbn, title, publisher, imprint_date, availability) VALUES ( ?, ?, ?, ?, ? )";
	private static final String UPDATE_BOOK = "UPDATE book SET isbn=?, title=?, publisher=?, imprint_date=?, availability=? WHERE id_book=?";
	private static final String DELETE_BOOK = "DELETE FROM book WHERE id_book=?";
	private static final String SEARCH_BY_TITLE = "SELECT * FROM book WHERE title LIKE '%?%'";
	private static final String SEARCH_BY_AUTHOR_SURNAME = "SELECT book.id_book, book.isbn, book.title, book.publisher, book.imprint_date, book.availability"
			+ "FROM book INNER JOIN book_author USING (id_book) INNER JOIN author USING (id_author)"
			+ "WHERE author.surname LIKE '%?%'";

	// DB table fields
	private static final String ID_BOOK = "id_book";
	private static final String ISBN = "isbn";
	private static final String TITLE = "title";
	private static final String PUBLISHER = "publisher";
	private static final String IMPRINT_DATE = "imprint_date";
	private static final String AVAILABILITY = "availability";

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
				ResultSet resultSet = query.executeQuery(SELECT_ALL_FROM_BOOK)) {
			while (resultSet.next()) {
				books.add(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return books;
	}

	@Override
	public Optional<Book> getById(Long id) {
		Optional<Book> book = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				book = Optional.of(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return book;
	}

	@Override
	public void create(Book book) {

		try (PreparedStatement query = connection.prepareStatement(CREATE_NEW_BOOK, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, book.getTitle());
			query.setString(2, book.getIsbn());
			query.setString(3, book.getPublisher());
			query.setDate(4, Date.valueOf(book.getImprintDate()));
			query.setString(5, book.getAvailability().getValue());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				book.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Book book) {

		try (PreparedStatement query = connection.prepareStatement(UPDATE_BOOK)) {
			query.setString(1, book.getTitle());
			query.setString(2, book.getIsbn());
			query.setString(3, book.getPublisher());
			query.setDate(4, Date.valueOf(book.getImprintDate()));
			query.setString(5, book.getAvailability().getValue());
			query.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_BOOK)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Book> searchByTitle(String title) {
		List<Book> books = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BY_TITLE)) {
			query.setString(1, title);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return books;
	}

	@Override
	public List<Book> searchByAuthorSurname(String authorSurname) {
		List<Book> books = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BY_AUTHOR_SURNAME)) {
			query.setString(1, authorSurname);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				books.add(extractBookFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return books;
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

	private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {

		return new Book.Builder().setId(resultSet.getLong(ID_BOOK)).setIsbn(resultSet.getString(ISBN))
				.setTitle(resultSet.getString(TITLE)).setPublisher(resultSet.getString(PUBLISHER))
				.setImprintDate(
						resultSet.getDate(IMPRINT_DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
				.setAvailability(Availability.valueOf(resultSet.getString(AVAILABILITY))).build();
	}

}
