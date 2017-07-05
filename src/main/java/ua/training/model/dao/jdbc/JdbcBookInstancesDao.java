package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.exception.ServerException;
import ua.training.model.dao.BookInstancesDao;
import ua.training.model.entity.Book;
import ua.training.model.entity.BookInstance;
import ua.training.model.entity.Status;

public class JdbcBookInstancesDao implements BookInstancesDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcBookInstancesDao.class);

	// queries
	private static String GET_BOOK_INSTANCES = "SELECT * FROM book_instance WHERE id_book=?";

	// fields
	private static String ID_BOOK_INSTANCE = "id_book_instance";
	private static String STATUS = "status";
	private static String INVENTORY_NUMBER = "inventory_number";
	private static String ID_BOOK = "id_book";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcBookInstancesDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcBookInstancesDao(Connection connection, boolean connectionShouldBeClosed) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(BookInstance e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(BookInstance e) {
		// TODO Auto-generated method stub

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
			LOGGER.error("JdbcBookInstancesDao getBookInstances SQL error: " + bookId, e);
			throw new ServerException(e);
		}
		return bookInstances;
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
				LOGGER.error("JdbcBookInstancesDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}

}
