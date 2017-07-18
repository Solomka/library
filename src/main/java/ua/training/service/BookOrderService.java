package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.constants.AppConstants;
import ua.training.dao.BookInstanceDao;
import ua.training.dao.BookOrderDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.entity.Librarian;
import ua.training.entity.Reader;
import ua.training.entity.Status;
import ua.training.exception.ServiceException;
import ua.training.locale.Message;

public class BookOrderService {

	private static final Logger LOGGER = LogManager.getLogger(BookOrderService.class);

	private DaoFactory daoFactory;

	BookOrderService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookOrderService INSTANCE = new BookOrderService(DaoFactory.getDaoFactory());
	}

	public static BookOrderService getInstance() {
		return Holder.INSTANCE;
	}

	public List<BookOrder> getAllOrders() {
		LOGGER.info("Get all orders");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getAll();
		}
	}

	public List<BookOrder> getNotReturnedReaderOrders(Long readerId) {
		LOGGER.info("Get not returned reader orders: " + readerId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getNotReturnedReaderOrders(readerId);
		}
	}

	public List<BookOrder> getUnfulfilledOrders() {
		LOGGER.info("Get unfulfilled orders");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getUnfulfilledOrders();
		}
	}

	/**
	 * Returns subscription book instances orders that were not returned on time
	 * 
	 * @return outstanding orders
	 */
	public List<BookOrder> getOutstandingOrders() {
		LOGGER.info("Get outstanding orders");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getOutstandingOrders();
		}
	}

	public List<BookOrder> searchNotReturnedOrdersByReaderCardNumber(String readerCardNumber) {
		LOGGER.info("Search not returned orders by readerCardNumber: " + readerCardNumber);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.searchNotReturnedOrdersByReaderCardNumber(readerCardNumber);
		}
	}

	/**
	 * Returns reading room book instances orders that were not returned on time
	 * 
	 * @return
	 */
	public List<BookOrder> getOrdersForReadingRoomReturn() {
		LOGGER.info("Get orders for reading room return: ");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getOrdersForReadingRoomReturn();
		}
	}

	public void createOrder(Long readerId, Long bookInstanceId) {
		LOGGER.info("Create order for book instance: " + bookInstanceId);
		BookOrder order = buildOrder(readerId, bookInstanceId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);

			Optional<BookInstance> optionalBookInsatnce = bookInstanceDao.getById(bookInstanceId);

			if (!optionalBookInsatnce.isPresent()) {
				LOGGER.error("Such bookInstance doesn't exist: " + bookInstanceId);
				throw new ServiceException(Message.BOOK_INSTANCE_IS_NOT_FOUND + bookInstanceId);
			}

			BookInstance bookInsatnce = optionalBookInsatnce.get();

			if (bookInsatnce.getStatus().equals(Status.UNAVAILABLE)) {
				LOGGER.error("Such bookInstance is already ordered: " + bookInstanceId);
				throw new ServiceException(Message.BOOK_INSTANCE_IS_ALREADY_ORDERED);
			}

			checkReaderOrderCreationAbility(bookOrderDao, readerId, bookInstanceId);

			bookInsatnce.setStatus(Status.UNAVAILABLE);
			bookOrderDao.create(order);
			bookInstanceDao.update(bookInsatnce);
			connection.commit();
		}
	}

	/**
	 * Check if reader has already ordered same book instance or max number of
	 * allowed orders is exeeded
	 * 
	 * @param bookOrderDao
	 * @param readerId
	 *            id reader that performs order creation
	 * @param bookInstanceId
	 *            id book instance that's going to be ordered
	 */
	private void checkReaderOrderCreationAbility(BookOrderDao bookOrderDao, Long readerId, Long bookInstanceId) {
		int unreturnedBookInstancesNumber = bookOrderDao.countUnreturnedBookInstancesNumber(readerId);
		int unreturnedSameBookInstancesNumber = bookOrderDao.countUnreturnedSameBookInstancesNumber(readerId,
				bookInstanceId);

		if (!isReaderAllowedToCreateConcreteBookOrder(unreturnedSameBookInstancesNumber)) {
			LOGGER.error("Can't create order 'cause reader with id: " + readerId
					+ " has already ordered same book instance");
			throw new ServiceException(Message.SAME_BOOK_INSTANCES_ORDER_CREATION_RESTRICTION);
		}

		if (!isReaderAllowedToCreateBookOrder(unreturnedBookInstancesNumber)) {
			LOGGER.error("Can't create order 'cause max number of allowed orders is exeeded for reader with id: "
					+ readerId);
			throw new ServiceException(Message.BOOK_INSTANCES_MAX_NUMBER_ORDER_CREATION_RESTRICTION);
		}

	}

	private BookOrder buildOrder(Long readerId, Long bookInsatnceId) {
		BookOrder order = new BookOrder.Builder().setCreationDate(getCurrentLocalDate())
				.setReader(new Reader.Builder().setId(readerId).build())
				.setBookInstance(new BookInstance.Builder().setId(bookInsatnceId).build()).build();
		return order;
	}

	/**
	 * Check if reader hasn't already ordered max allowed number of book
	 * instances
	 * 
	 * @param unreturnedReaderBookInstancesNumber
	 *            number of reader's ordered book instances
	 * @return true if reader hasn't already ordered max allowed number of book
	 *         instances; false otherwise
	 */
	private boolean isReaderAllowedToCreateBookOrder(int unreturnedReaderBookInstancesNumber) {
		return unreturnedReaderBookInstancesNumber < AppConstants.UNRETURNED_READER_BOOK_INSTANCES_MAX_NUMBER;
	}

	/**
	 * Check if reader has already ordered bookInstance of the same book
	 * 
	 * @param unreturnedSameBookInstancesNumber
	 *            number of reader's unreturned same book instance's orders
	 * @return true if reader hasn't ordered same book instance; false otherwise
	 */
	private boolean isReaderAllowedToCreateConcreteBookOrder(int unreturnedSameBookInstancesNumber) {
		return (unreturnedSameBookInstancesNumber == 0);
	}

	public void fulfilOrder(Long orderId, Long librarianId) {
		LOGGER.info("Fulfil order: " + orderId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			isOrderPresent(orderId, optionalOrder);
			BookOrder order = optionalOrder.get();

			if (isOrderFulfilled(order)) {
				LOGGER.error("Can't fulfill order 'cause it's already fulfilled: " + orderId);
				throw new ServiceException(Message.ORDER_ALREADY_FULFILLED);
			}
			order.setFulfilmentDate(getCurrentLocalDate());
			order.setLibrarian(new Librarian.Builder().setId(librarianId).build());
			bookOrderDao.update(order);
			connection.commit();
		}
	}

	public void issueOrder(Long orderId) {
		LOGGER.info("Issue order: " + orderId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			isOrderPresent(orderId, optionalOrder);
			BookOrder order = optionalOrder.get();

			checkOrderIssuanceAbility(orderId, order);

			LocalDate pickUpDate = getCurrentLocalDate();
			order.setPickUpDate(pickUpDate);
			order.setReturnDate(getReturnOrderDate(pickUpDate));
			bookOrderDao.update(order);
			connection.commit();
		}
	}

	/**
	 * Check if order is not already fulfilled or is already issued
	 * 
	 * @param orderId
	 * @param order
	 */
	private void checkOrderIssuanceAbility(Long orderId, BookOrder order) {
		if (!isOrderFulfilled(order)) {
			LOGGER.error("Can't issue Order 'cause Order is not fulfilled: " + orderId);
			throw new ServiceException(Message.ORDER_IS_NOT_FULFILLED);
		}
		if (isOrderIssued(order)) {
			LOGGER.error("Can't issue Order 'cause it's already issued: " + orderId);
			throw new ServiceException(Message.BOOK_ALREADY_ISSUED);
		}
	}

	public void returnOrder(Long orderId) {
		LOGGER.info("Return order: " + orderId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);

			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			isOrderPresent(orderId, optionalOrder);
			BookOrder order = optionalOrder.get();

			checkOrderReturningAbility(orderId, order);

			prepareOrderForReturning(order);
			bookOrderDao.update(order);
			bookInstanceDao.update(order.getBookInstance());
			connection.commit();
		}
	}

	/**
	 * Check if order is not already fulfilled or is already issued or is
	 * already returned
	 * 
	 * @param orderId
	 * @param order
	 */
	private void checkOrderReturningAbility(Long orderId, BookOrder order) {
		if (!isOrderFulfilled(order)) {
			LOGGER.error("Can't return Order 'cause it's not fulfilled: " + orderId);
			throw new ServiceException(Message.ORDER_RETURN_IS_NOT_FULFILLED);
		}
		if (!isOrderIssued(order)) {
			LOGGER.error("Can't return Order 'cause it's not issued: " + orderId);
			throw new ServiceException(Message.BOOK_IS_NOT_ISSUED);
		}
		if (isOrderReturned(order)) {
			LOGGER.error("Can't return book 'cause it's already returned: " + orderId);
			throw new ServiceException(Message.BOOK_ALREADY_RETURNED);
		}
	}

	private void isOrderPresent(Long orderId, Optional<BookOrder> optionalOrder) {
		if (!optionalOrder.isPresent()) {
			LOGGER.error("Such order doesn't exist: " + orderId);
			throw new ServiceException(Message.BOOK_ORDER_IS_NOT_FOUND + orderId);
		}
	}

	private void prepareOrderForReturning(BookOrder order) {
		order.setActualReturnDate(getCurrentLocalDate());
		BookInstance bookInstance = order.getBookInstance();
		bookInstance.setStatus(Status.AVAILABLE);
	}

	private boolean isOrderFulfilled(BookOrder order) {
		return order.getFulfilmentDate() != null;
	}

	private boolean isOrderIssued(BookOrder order) {
		return order.getPickUpDate() != null && order.getReturnDate() != null;
	}

	private boolean isOrderReturned(BookOrder order) {
		return order.getActualReturnDate() != null;
	}

	LocalDate getCurrentLocalDate() {
		return LocalDate.now();
	}

	private LocalDate getReturnOrderDate(LocalDate pickUpDate) {
		return pickUpDate.plusMonths(AppConstants.RETURN_BOOK_MONTHS_PERIOD);
	}
}