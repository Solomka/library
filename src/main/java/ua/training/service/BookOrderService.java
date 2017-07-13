package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.constants.AppConstants;
import ua.training.controller.session.SessionManager;
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

	public List<BookOrder> getUnfulfilledOrders() {
		LOGGER.info("Get unexecuted orders");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getUnexecutedOrders();
		}
	}

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

	public List<BookOrder> getNotReturnedReaderOrders(Long readerId) {
		LOGGER.info("Get not returned reader orders: " + readerId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getNotReturnedReaderOrders(readerId);
		}
	}

	/*
	 * public List<BookOrder> getOrdersForCancellation() {
	 * LOGGER.info("Get orders for cacellation: "); try (BookOrderDao
	 * bookOrderDao = daoFactory.createBookOrderDao()) { return
	 * bookOrderDao.getOrdersForCancellation(); } }
	 */

	public List<BookOrder> getOrdersForReadingRoomReturn() {
		LOGGER.info("Get orders for reading room return: ");
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getOrdersForReadingRoomReturn();
		}
	}

	/*
	 * public List<BookOrder> getExecutedReaderOrders(Long readerId) {
	 * LOGGER.info("Get executed reader orders: " + readerId); try (BookOrderDao
	 * bookOrderDao = daoFactory.createBookOrderDao()) { return
	 * bookOrderDao.getExecutedReaderOrders(readerId); } }
	 * 
	 * public List<BookOrder> getOutstandingReaderOrders(Long readerId) {
	 * LOGGER.info("Get outstanding reader orders: " + readerId); try
	 * (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) { return
	 * bookOrderDao.getOutstandingReaderOrders(readerId); } }
	 */
	public void createOrder(Long readerId,Long bookInsatnceId) {
		LOGGER.info("Create order for book instance: " + bookInsatnceId);
		BookOrder order = new BookOrder.Builder().setCreationDate(getCurrentLocalDate()).setReader(new Reader.Builder().setId(readerId).build()).setBookInstance(new BookInstance.Builder()
				.setId(bookInsatnceId).build()).build();
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);	
			
			Optional<BookInstance> optionalBookInsatnce = bookInstanceDao.getById(bookInsatnceId);
			BookInstance bookInsatnce = optionalBookInsatnce.get();
			bookInsatnce.setStatus(Status.UNAVAILABLE);			
			
			bookOrderDao.create(order);
			bookInstanceDao.update(bookInsatnce);
			connection.commit();
		}
	}

	public void fulfilOrder(Long orderId, Long librarianId) {
		LOGGER.info("Fulfil order: " + orderId);
		// class level dao
		// connection will be closed regardless of exception
		// here transaction is closed but connection is not
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			if (!isOrderPresent(optionalOrder)) {
				throw new ServiceException(Message.INVALID_BOOK_ORDER_ID + orderId);
			}

			BookOrder order = optionalOrder.get();

			if (isOrderFulfilled(order)) {
				LOGGER.error("Can't fulfill order 'cause it's already fulfilled: " + orderId);
				throw new ServiceException(Message.ORDER_ALREADY_FULFILLED);
			}
			order.setFulfilmentDate(getCurrentLocalDate());
			order.setLibrarian(new Librarian.Builder().setId(librarianId).build());
			bookOrderDao.fulfilOrder(order);
		}
	}

	public void issueOrder(Long orderId) {
		LOGGER.info("Issue order: " + orderId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			if (!isOrderPresent(optionalOrder)) {
				throw new ServiceException(Message.INVALID_BOOK_ORDER_ID + orderId);
			}

			BookOrder order = optionalOrder.get();

			if (!isOrderFulfilled(order)) {
				LOGGER.error("Can't issue Order 'cause Order is not fulfilled: " + orderId);
				throw new ServiceException(Message.ORDER_IS_NOT_FULFILLED);
			}
			if (isBookIssued(order)) {
				LOGGER.error("Can't issue Order 'cause it's already issued: " + orderId);
				throw new ServiceException(Message.BOOK_ALREADY_ISSUED);
			}
			LocalDate pickUpDate = getCurrentLocalDate();
			order.setPickUpDate(pickUpDate);
			order.setReturnDate(getReturnOrderDate(pickUpDate));
			bookOrderDao.issueOrder(order);
		}
	}

	public void backOrderToReadingRoom(Long orderId) {
		LOGGER.info("Return order to reading room: " + orderId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			if (!isOrderPresent(optionalOrder)) {
				throw new ServiceException(Message.INVALID_BOOK_ORDER_ID + orderId);
			}

			BookOrder order = optionalOrder.get();

			LocalDate actualReturnDate = getCurrentLocalDate();
			order.setActualReturnDate(actualReturnDate);
			BookInstance bookInstance = order.getBookInstance();
			bookInstance.setStatus(Status.AVAILABLE);
			bookOrderDao.returnOrder(order);
			bookInstanceDao.update(bookInstance);
			connection.commit();
		}
	}

	public void returnOrder(Long orderId) {
		LOGGER.info("Return order: " + orderId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);
			Optional<BookOrder> optionalOrder = bookOrderDao.getById(orderId);

			if (!isOrderPresent(optionalOrder)) {
				throw new ServiceException(Message.INVALID_BOOK_ORDER_ID + orderId);
			}

			BookOrder order = optionalOrder.get();

			if (!isOrderFulfilled(order)) {
				LOGGER.error("Can't return Order 'cause it's not fulfilled: " + orderId);
				throw new ServiceException(Message.ORDER_RETURN_IS_NOT_FULFILLED);
			}
			if (!isBookIssued(order)) {
				LOGGER.error("Can't return Order 'cause it's not issued: " + orderId);
				throw new ServiceException(Message.BOOK_IS_NOT_ISSUED);
			}
			if (isBookReturned(order)) {
				LOGGER.error("Can't return book 'cause it's already returned: " + orderId);
				throw new ServiceException(Message.BOOK_ALREADY_RETURNED);
			}
			LocalDate actualReturnDate = getCurrentLocalDate();
			order.setActualReturnDate(actualReturnDate);
			BookInstance bookInstance = order.getBookInstance();
			bookInstance.setStatus(Status.AVAILABLE);
			bookOrderDao.returnOrder(order);
			bookInstanceDao.update(bookInstance);
			connection.commit();
		}
	}

	private boolean isOrderPresent(Optional<BookOrder> optionalOrder) {
		return (optionalOrder.isPresent() ? true : false);
	}

	private boolean isOrderFulfilled(BookOrder order) {
		return order.getFulfilmentDate() != null;
	}

	private boolean isBookIssued(BookOrder order) {
		return order.getPickUpDate() != null && order.getReturnDate() != null;
	}

	private boolean isBookReturned(BookOrder order) {
		return order.getActualReturnDate() != null;
	}

	private LocalDate getCurrentLocalDate() {
		return LocalDate.now();
	}

	private LocalDate getReturnOrderDate(LocalDate pickUpDate) {
		return pickUpDate.plusMonths(AppConstants.RETURN_BOOK_MONTHS_PERIOD);
	}

}
