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

import ua.training.dao.BookInstanceDao;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.Status;
import ua.training.exception.ServerException;

public class JdbcBookInstanceDao implements BookInstanceDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcBookInstanceDao.class);

	// queries
	private static String ADD_BOOK_INSTANCE = "INSERT INTO book_instance (inventory_number, status, id_book) VALUES (?, ?, ?)";
	private static String GET_BOOK_INSTANCE_BY_ID = "SELECT * FROM book_instance WHERE id_book_instance=?";
	private static String GET_BOOK_INSTANCES = "SELECT * FROM book_instance WHERE id_book=?";
	private static String UPDATE_BOOK_INSTANCE = "UPDATE book_instance SET status=? WHERE id_book_instance=?";

	// fields
	private static String ID_BOOK_INSTANCE = "id_book_instance";
	private static String STATUS = "status";
	private static String INVENTORY_NUMBER = "inventory_number";
	private static String ID_BOOK = "id_book";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcBookInstanceDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcBookInstanceDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<BookInstance> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BookInstance> getById(Long id) {
		Optional<BookInstance> bookInstance = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_INSTANCE_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				bookInstance = Optional.of(extractBookInstanceFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao getById SQL error: " + id, e);
			throw new ServerException(e);
		}
		return bookInstance;
	}

	@Override
	public void create(BookInstance e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(BookInstance bookInstance) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_BOOK_INSTANCE)) {
			query.setString(1, bookInstance.getStatus().getValue());
			query.setLong(2, bookInstance.getId());
			query.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao update SQL error: " + bookInstance.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BookInstance> getBookInstances(Long bookId) {
		List<BookInstance> bookInstances = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_BOOK_INSTANCES)) {
			query.setLong(1, bookId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookInstances.add(extractBookInstanceFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao getBookInstances SQL error: " + bookId, e);
			throw new ServerException(e);
		}
		return bookInstances;
	}

	@Override
	public void addBookInstance(BookInstance bookInstance) {
		try (PreparedStatement query = connection.prepareStatement(ADD_BOOK_INSTANCE,
				Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, bookInstance.getInventoryNumber());
			query.setString(2, bookInstance.getStatus().getValue());
			query.setLong(3, bookInstance.getBook().getId());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				bookInstance.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao create SQL error: " + bookInstance.toString(), e);
			throw new ServerException(e);
		}

	}

	private BookInstance extractBookInstanceFromResultSet(ResultSet resultSet) throws SQLException {
		return new BookInstance.Builder().setId(resultSet.getLong(ID_BOOK_INSTANCE))
				.setStatus(Status.forValur(resultSet.getString(STATUS)))
				.setInventoryNumber(resultSet.getString(INVENTORY_NUMBER))
				.setBook(new Book.Builder().setId(resultSet.getLong(ID_BOOK)).build()).build();
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcBookInstanceDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}
}
