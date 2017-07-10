package ua.training.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.BookInstanceDao;
import ua.training.dao.BookOrderDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.entity.BookOrder;

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

	public List<BookOrder> getUnexecutedOrders() {
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

	public List<BookOrder> searchOrdersByReaderCardNumber(String readerCardNumber) {
		LOGGER.info("Search orders by readerCardNumber: " + readerCardNumber);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.searchOrdersByReaderCardNumber(readerCardNumber);
		}
	}

	public List<BookOrder> getAllReaderOrders(Long readerId) {
		LOGGER.info("Get all reader orders: " + readerId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getAllReaderOrders(readerId);
		}
	}

	public List<BookOrder> getExecutedReaderOrders(Long readerId) {
		LOGGER.info("Get executed reader orders: " + readerId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getExecutedReaderOrders(readerId);
		}
	}

	public List<BookOrder> getOutstandingReaderOrders(Long readerId) {
		LOGGER.info("Get outstanding reader orders: " + readerId);
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			return bookOrderDao.getOutstandingReaderOrders(readerId);
		}
	}

	public void createOrder(BookOrder order) {
		LOGGER.info("Create order: " + order.toString());
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);
			bookOrderDao.create(order);
			bookInstanceDao.update(order.getBookInstance());
			connection.commit();
		}
	}

	public void executeOrder(BookOrder order) {
		LOGGER.info("Execute order: " + order.getId());
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			bookOrderDao.executeOrder(order);
		}
	}

	public void issueBook(BookOrder order) {
		LOGGER.info("Issue book: " + order.getId());
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			bookOrderDao.issueBook(order);
		}
	}

	public void getBackBook(BookOrder order) {
		LOGGER.info("Get back book: " + order.getId());
		try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
			bookOrderDao.getBackBook(order);
		}
	}

}
