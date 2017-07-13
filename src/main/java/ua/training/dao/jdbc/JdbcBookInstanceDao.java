package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static String GET_BOOK_INSTANCE_BY_ID = "SELECT * FROM book_instance WHERE id_book_instance=?";
	private static String CREATE_BOOK_INSTANCE = "INSERT INTO book_instance (inventory_number, status, id_book) VALUES (?, ?, ?)";
	private static String UPDATE_BOOK_INSTANCE = "UPDATE book_instance SET status=?, inventory_number=? WHERE id_book_instance=?";
	private static String DELETE_BOOK_INSTANCE = "DELETE FROM book_instance WHERE id_book_instance=?";

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
		throw new UnsupportedOperationException();
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
			LOGGER.error("JdbcBookInstanceDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return bookInstance;
	}

	@Override
	public void create(BookInstance bookInstance) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_BOOK_INSTANCE,
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
			LOGGER.error("JdbcBookInstanceDao create SQL exception: " + bookInstance.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(BookInstance bookInstance) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_BOOK_INSTANCE)) {
			query.setString(1, bookInstance.getStatus().getValue());
			query.setString(2, bookInstance.getInventoryNumber());
			query.setLong(3, bookInstance.getId());
			query.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao update SQL exception: " + bookInstance.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_BOOK_INSTANCE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookInstanceDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	private BookInstance extractBookInstanceFromResultSet(ResultSet resultSet) throws SQLException {
		return new BookInstance.Builder().setId(resultSet.getLong(Column.BOOK_INSTANCE_ID))
				.setStatus(Status.forValue(resultSet.getString(Column.BOOK_INSTANCE_STATUS)))
				.setInventoryNumber(resultSet.getString(Column.BOOK_INSTANCE_INVENTORY_NUMBER))
				.setBook(new Book.Builder().setId(resultSet.getLong(Column.BOOK_INSTANCE_ID_BOOK)).build()).build();
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
