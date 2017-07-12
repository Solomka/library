package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.BookOrderDao;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.entity.Reader;
import ua.training.entity.Status;
import ua.training.exception.ServerException;

public class JdbcBookOrderDao implements BookOrderDao {

	// queries
	private static final Logger LOGGER = LogManager.getLogger(JdbcBookOrderDao.class);

	private static String GET_ALL_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " ORDER BY creation_date DESC";
	private static String GET_NOT_RETURNED_READER_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE actual_return_date IS NULL  AND id_reader=?" + " ORDER BY creation_date DESC";
	private static String GET_ORDER_BY_ID = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date, inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE id_order=?";
	private static String GET_UNEXECUTED_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date, inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date IS NULL" + " ORDER BY creation_date ASC";
	private static String GET_OUTSTANDING_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date, inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date IS NOT NULL AND pickup_date IS NOT NULL"
			+ " AND actual_return_date IS NULL AND CURDATE() > return_date" + " ORDER BY return_date ASC";
	/*private static String GET_EXECUTED_READER_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date, inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date IS NOT NULL AND pickup_date IS NULL AND id_reader=?"
			+ " ORDER BY creation_date DESC";
	private static String GET_OUTSTANDING_READER_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date, inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date IS NOT NULL AND pickup_date IS NOT NULL"
			+ " AND actual_return_date IS NULL AND CURDATE() > return_date  AND reader_id=?" + " ORDER BY return_date ASC";*/
	private static String SEARCH_NOT_RETURNED_ORDERS_BY_READER_CARD_NUMBER = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE actual_return_date IS NULL AND reader_card_number=?" + " ORDER BY creation_date DESC";
	private static String GET_ORDERS_FOR_CANCELLATION = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " inventory_number, reader_card_number"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date <= CURDATE() - INETRVAL ? DAY"
			+ " ORDER BY fulfilment_date ASC";
	private static String FULFIL_ORDER = "UPDATE book_order SET fulfilment_date=?, id_librarian=?"
			+ " WHERE id_order=?";
	private static String ISSUE_ORDER = "UPDATE book_order SET pickup_date=?, return_date=?" + " WHERE id_order=?";
	private static String RETURN_ORDER = "UPDATE book_order JOIN book_instance USING (id_book_instance) SET actual_return_date=?"
			+ " WHERE id_order=?";
	private static String CREATE_ORDER = "INSERT INTO book_order (creation_date, id_book_instance, id_reader) VALUES (?, ?, ?)";

	// bookOrder fields
	private static String ID_ORDER = "id_order";
	private static String CREATION_DATE = "creation_date";
	private static String FULFILMENT_DATE = "fulfilment_date";
	private static String PICKUP_DATE = "pickup_date";
	private static String RETURN_DATE = "return_date";
	private static String ACTUAL_RETURN_DATE = "actual_return_date";

	private static String INVENTORY_NUMBER = "inventory_number";
	private static String READER_CARD_NUMBER = "reader_card_number";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcBookOrderDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcBookOrderDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<BookOrder> getAll() {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL_ORDERS)) {
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getAll SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public Optional<BookOrder> getById(Long id) {
		Optional<BookOrder> bookOrder = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_ORDER_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				bookOrder = Optional.of(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getById SQL error: " + id, e);
			throw new ServerException(e);
		}
		return bookOrder;
	}

	@Override
	public List<BookOrder> getUnexecutedOrders() {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_UNEXECUTED_ORDERS)) {
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getUnexecutedOrders SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public List<BookOrder> getOutstandingOrders() {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_OUTSTANDING_ORDERS)) {
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getOutstandingOrders SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	List<BookOrder> bookOrders = new ArrayList<>();

	@Override
	public List<BookOrder> searchNotReturnedOrdersByReaderCardNumber(String readerCardNumber) {
		List<BookOrder> bookOrders = new ArrayList<>();
		try (PreparedStatement query = connection.prepareStatement(SEARCH_NOT_RETURNED_ORDERS_BY_READER_CARD_NUMBER)) {
			query.setString(1, readerCardNumber);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao searchOrdersByReaderCardNumber SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public List<BookOrder> getNotReturnedReaderOrders(Long readerId) {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_NOT_RETURNED_READER_ORDERS)) {
			query.setLong(1, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getAllReaderOrders SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	/*@Override
	public List<BookOrder> getExecutedReaderOrders(Long readerId) {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_EXECUTED_READER_ORDERS)) {
			query.setLong(1, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getExecutedReaderOrders SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public List<BookOrder> getOutstandingReaderOrders(Long readerId) {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_OUTSTANDING_READER_ORDERS)) {
			query.setLong(1, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getOutstandingReaderOrders SQL error", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}*/

	@Override
	public void create(BookOrder bookOrder) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
			query.setDate(1, Date.valueOf(bookOrder.getCreationDate()));
			query.setLong(2, bookOrder.getBookInstance().getId());
			query.setLong(3, bookOrder.getReader().getId());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				bookOrder.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao create SQL error: " + bookOrder.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(BookOrder bookOrder) {
		/*
		 * try (PreparedStatement query =
		 * connection.prepareStatement(UPDATE_ORDER)) { query.setTimestamp(1,
		 * Timestamp.valueOf(bookOrder.getFulfilmentDate()));
		 * query.setTimestamp(1, Timestamp.valueOf(bookOrder.getPickUpDate()));
		 * query.setTimestamp(2, Timestamp.valueOf(bookOrder.getReturnDate()));
		 * query.setTimestamp(3,
		 * Timestamp.valueOf(bookOrder.getActualReturnDate())); query.setLong(4,
		 * bookOrder.getLibrarian().getId()); query.setLong(5,
		 * bookOrder.getId()); query.executeUpdate();
		 * 
		 * } catch (SQLException e) {
		 * LOGGER.error("JdbcBookOrderDao update SQL error: " +
		 * bookOrder.toString(), e); throw new ServerException(e); }
		 */

	}

	@Override
	public void fulfilOrder(BookOrder bookOrder) {
		try (PreparedStatement query = connection.prepareStatement(FULFIL_ORDER)) {
			query.setDate(1, Date.valueOf(bookOrder.getFulfilmentDate()));
			query.setLong(2, bookOrder.getLibrarian().getId());
			query.setLong(3, bookOrder.getId());
			query.execute();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao fulfilOrder SQL error: " + bookOrder.getId(), e);
			throw new ServerException(e);

		}
	}

	@Override
	public void issueOrder(BookOrder bookOrder) {
		try (PreparedStatement query = connection.prepareStatement(ISSUE_ORDER)) {
			query.setDate(1, Date.valueOf(bookOrder.getPickUpDate()));
			query.setDate(2, Date.valueOf(bookOrder.getReturnDate()));
			query.setLong(3, bookOrder.getId());
			query.execute();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao issueOrder SQL error: " + bookOrder.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void returnOrder(BookOrder bookOrder) {
		try (PreparedStatement query = connection.prepareStatement(RETURN_ORDER)) {
			query.setDate(1, Date.valueOf(bookOrder.getActualReturnDate()));
			query.setLong(2, bookOrder.getId());
			query.execute();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao returnOrder SQL error: " + bookOrder.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	private BookOrder extractBookOrder(ResultSet resultSet) throws SQLException {
		return new BookOrder.Builder().setId(resultSet.getLong(ID_ORDER))
				.setCreationDate(resultSet.getDate(CREATION_DATE).toLocalDate())
				.setFulfilmentDate(checkResultSetLocalDateValue(resultSet, FULFILMENT_DATE))
				.setPickUpDate(checkResultSetLocalDateValue(resultSet, PICKUP_DATE))
				.setReturnDate(checkResultSetLocalDateValue(resultSet, RETURN_DATE))
				.setActualReturnDate(checkResultSetLocalDateValue(resultSet, ACTUAL_RETURN_DATE))
				.setBookInstance(
						new BookInstance.Builder().setInventoryNumber(resultSet.getString(INVENTORY_NUMBER)).build())
				.setReader(new Reader.Builder().setReaderCardNumber(resultSet.getString(READER_CARD_NUMBER)).build())
				.build();
	}
	
	private LocalDate checkResultSetLocalDateValue(ResultSet resultSet, String dataFieldName) throws SQLException{
		return (resultSet.getDate(dataFieldName) == null)? null: resultSet.getDate(dataFieldName).toLocalDate();
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
				LOGGER.info("Connection is closed");
			} catch (SQLException e) {
				LOGGER.error("JdbcBookOrderDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}

}
