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
		try(BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()){
			return bookOrderDao.getAll();
		}
	}

	public List<BookOrder> getUnexecutedOrders() {
		return null;
	}

	public List<BookOrder> getOutstandingOrders() {
		return null;
	}
	
	//sorted by date
	public List<BookOrder> searchOrdersByReaderCardNumber(String readerCardNumber){
		return null;
	}
	
	//sorted by date
	public List<BookOrder> getAllReaderOrders(Long readerId) {
		return null;
	}
	
	//executed but not taken
	public List<BookOrder> getExecutedReaderOrders(Long readerId) {
		return null;
	}
	
	public List<BookOrder> getOutstandingReaderOrders(Long readerId) {
		return null;
	}

	public void createOrder(BookOrder order) {
		LOGGER.info("Create order: " + order.toString());
		try(DaoConnection connection = daoFactory.getConnection()){
			connection.begin();
			BookOrderDao bookOrderDao = daoFactory.createBookOrderDao(connection);
			BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao(connection);
			bookOrderDao.create(order);
			bookInstanceDao.update(order.getBookInstance());
			connection.commit();			
		}
	}

	public void executeOrder(Long orderId) {

	}

	public void issueBook(Long orderId) {

	}

	public void getBackBook(Long orderId) {

	}

}
