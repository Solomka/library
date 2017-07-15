package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.BookOrderDao;
import ua.training.entity.Book;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.entity.Librarian;
import ua.training.entity.Reader;
import ua.training.entity.Status;
import ua.training.exception.ServerException;

public class JdbcBookOrderDao implements BookOrderDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcBookOrderDao.class);

	private static String GET_ALL_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " ORDER BY creation_date DESC";
	private static String GET_ORDER_BY_ID = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE id_order=?";
	private static String CREATE_ORDER = "INSERT INTO book_order (creation_date, id_book_instance, id_reader) VALUES (?, ?, ?)";
	private static String UPDATE_ORDER = "UPDATE book_order SET fulfilment_date=?, pickup_date=?, return_date=?, actual_return_date=?, id_librarian=?"
			+ " WHERE id_order=?";
	private static String DELETE_ORDER = "DELETE FROM book_order WHERE id_order=?";
	private static String GET_NOT_RETURNED_READER_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE actual_return_date IS NULL  AND id_reader=? AND id_order NOT IN (SELECT id_order"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance) JOIN book USING(id_book)"
			+ " WHERE return_date IS NOT NULL AND CURDATE() > return_date AND actual_return_date IS NULL AND availability='reading room' AND id_reader=?)"
			+ " ORDER BY creation_date DESC";
	private static String COUNT_UNRETURNED_BOOK_INSTANCES_NUMBER = "SELECT COUNT(*) AS unreturned_book_instances"
			+ " FROM (SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE actual_return_date IS NULL AND id_reader=? AND id_order NOT IN (SELECT id_order"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance) JOIN book USING(id_book)"
			+ " WHERE return_date IS NOT NULL AND CURDATE() > return_date AND actual_return_date IS NULL AND availability='reading room' AND id_reader=?)) AS tb";
	private static String COUNT_UNRETURNED_SAME_BOOK_INSTANCES_NUMBER = "SELECT COUNT(id_order) unreturned_same_book_instances"
			+ " FROM book_order JOIN reader USING(id_reader) JOIN book_instance USING(id_book_instance) JOIN book USING (id_book)"
			+ " WHERE id_reader = ? AND actual_return_date IS NULL AND id_book IN (SELECT id_book"
			+ " FROM book_instance" + " WHERE id_book_instance = ?) AND id_order NOT IN (SELECT id_order"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance) JOIN book USING(id_book)"
			+ " WHERE return_date IS NOT NULL AND CURDATE() > return_date AND actual_return_date IS NULL AND availability='reading room' AND id_reader=?)";
	private static String GET_UNEXECUTED_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE fulfilment_date IS NULL" + " ORDER BY creation_date ASC";
	private static String GET_OUTSTANDING_ORDERS = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING(id_reader) JOIN book_instance USING(id_book_instance) JOIN book USING (id_book)"
			+ " WHERE return_date IS NOT NULL AND CURDATE() > return_date AND actual_return_date IS NULL AND availability='subscription'"
			+ " ORDER BY return_date ASC";
	private static String SEARCH_NOT_RETURNED_ORDERS_BY_READER_CARD_NUMBER = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance)"
			+ " WHERE actual_return_date IS NULL AND reader_card_number=?" + " ORDER BY creation_date DESC";
	private static String GET_ORDERS_FOR_READING_ROOM_RETURN = "SELECT id_order, creation_date, fulfilment_date, pickup_date, return_date, actual_return_date,"
			+ " id_book_instance, status, inventory_number, id_book, id_reader, reader_card_number, id_librarian"
			+ " FROM book_order JOIN reader USING (id_reader) JOIN book_instance USING (id_book_instance) JOIN book USING(id_book)"
			+ " WHERE return_date IS NOT NULL AND CURDATE() > return_date AND actual_return_date IS NULL AND availability='reading room'";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	private static String UNRETURNED_BOOK_INSTANCES = "unreturned_book_instances";
	private static String UNRETURNED_SAME_BOOK_INSTANCES = "unreturned_same_book_instances";

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
			LOGGER.error("JdbcBookOrderDao getAll SQL exception", e);
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
			LOGGER.error("JdbcBookOrderDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return bookOrder;
	}

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
			LOGGER.error("JdbcBookOrderDao create SQL exception: " + bookOrder.toString(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(BookOrder bookOrder) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_ORDER)) {
			query.setDate(1, checkBookOrderLocalDateValue(bookOrder.getFulfilmentDate()));
			query.setDate(2, checkBookOrderLocalDateValue(bookOrder.getPickUpDate()));
			query.setDate(3, checkBookOrderLocalDateValue(bookOrder.getReturnDate()));
			query.setDate(4, checkBookOrderLocalDateValue(bookOrder.getActualReturnDate()));
			query.setObject(5, (bookOrder.getLibrarian() == null) ? null : bookOrder.getLibrarian().getId());
			query.setLong(6, bookOrder.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao update SQL exception: " + bookOrder.getId(), e);
			throw new ServerException(e);
		}

	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE_ORDER)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<BookOrder> getNotReturnedReaderOrders(Long readerId) {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(GET_NOT_RETURNED_READER_ORDERS)) {
			query.setLong(1, readerId);
			query.setLong(2, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getNotReturnedReaderOrders SQL exception: " + readerId, e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public int countUnreturnedBookInstancesNumber(Long readerId) {
		int unreturnedReaderBookInstancesNumber = 0;
		try (PreparedStatement query = connection.prepareStatement(COUNT_UNRETURNED_BOOK_INSTANCES_NUMBER)) {
			query.setLong(1, readerId);
			query.setLong(2, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				unreturnedReaderBookInstancesNumber = resultSet.getInt(UNRETURNED_BOOK_INSTANCES);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao countUnreturnedReaderBookInstancesNumber SQL exception: " + readerId, e);
			throw new ServerException(e);
		}
		return unreturnedReaderBookInstancesNumber;
	}

	@Override
	public int countUnreturnedSameBookInstancesNumber(Long readerId, Long bookInstanceId) {
		int unreturnedReaderBookInstancesNumber = 0;
		try (PreparedStatement query = connection.prepareStatement(COUNT_UNRETURNED_SAME_BOOK_INSTANCES_NUMBER)) {
			query.setLong(1, readerId);
			query.setLong(2, bookInstanceId);
			query.setLong(3, readerId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				unreturnedReaderBookInstancesNumber = resultSet.getInt(UNRETURNED_SAME_BOOK_INSTANCES);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao countUnreturnedSameBookInstancesNumber SQL exception: " + readerId, e);
			throw new ServerException(e);
		}
		return unreturnedReaderBookInstancesNumber;
	}

	@Override
	public List<BookOrder> getUnfulfilledOrders() {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_UNEXECUTED_ORDERS)) {
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getUnexecutedOrders SQL exception", e);
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
			LOGGER.error("JdbcBookOrderDao getOutstandingOrders SQL exception", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

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
			LOGGER.error("JdbcBookOrderDao searchOrdersByReaderCardNumber SQL exception: " + readerCardNumber, e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	@Override
	public List<BookOrder> getOrdersForReadingRoomReturn() {
		List<BookOrder> bookOrders = new ArrayList<>();

		try (Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(GET_ORDERS_FOR_READING_ROOM_RETURN)) {
			while (resultSet.next()) {
				bookOrders.add(extractBookOrder(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcBookOrderDao getOrdersForReadingRoomReturn SQL exception", e);
			throw new ServerException(e);
		}
		return bookOrders;
	}

	private BookOrder extractBookOrder(ResultSet resultSet) throws SQLException {
		return new BookOrder.Builder().setId(resultSet.getLong(Column.ORDER_ID))
				.setCreationDate(resultSet.getDate(Column.ORDER_CREATION_DATE).toLocalDate())
				.setFulfilmentDate(checkResultSetLocalDateValue(resultSet, Column.ORDER_FULFILMENT_DATE))
				.setPickUpDate(checkResultSetLocalDateValue(resultSet, Column.ORDER_PICKUP_DATE))
				.setReturnDate(checkResultSetLocalDateValue(resultSet, Column.ORDER_RETURN_DATE))
				.setActualReturnDate(checkResultSetLocalDateValue(resultSet, Column.ORDER_ACTUAL_RETURN_DATE))
				.setBookInstance(extractBookInstanceFromResultSet(resultSet))
				.setReader(new Reader.Builder().setId(resultSet.getLong(Column.ORDER_ID_READER))
						.setReaderCardNumber(resultSet.getString(Column.READER_READER_CARD_NUMBER)).build())
				.setLibrarian(new Librarian.Builder().setId(resultSet.getLong(Column.ORDER_ID_LIBRARIAN)).build())
				.build();
	}

	private BookInstance extractBookInstanceFromResultSet(ResultSet resultSet) throws SQLException {
		return new BookInstance.Builder().setId(resultSet.getLong(Column.BOOK_INSTANCE_ID))
				.setStatus(Status.forValue(resultSet.getString(Column.BOOK_INSTANCE_STATUS)))
				.setInventoryNumber(resultSet.getString(Column.BOOK_INSTANCE_INVENTORY_NUMBER))
				.setBook(new Book.Builder().setId(resultSet.getLong(Column.BOOK_INSTANCE_ID_BOOK)).build()).build();
	}

	private LocalDate checkResultSetLocalDateValue(ResultSet resultSet, String dataFieldName) throws SQLException {
		return (resultSet.getDate(dataFieldName) == null) ? null : resultSet.getDate(dataFieldName).toLocalDate();
	}

	private Date checkBookOrderLocalDateValue(LocalDate localDate) {
		return (localDate == null) ? null : Date.valueOf(localDate);
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
