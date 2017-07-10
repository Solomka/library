package ua.training.dao;

import java.util.List;

import ua.training.entity.BookOrder;

public interface BookOrderDao extends GenericDao<BookOrder, Long>, AutoCloseable {
	
	List<BookOrder> getUnexecutedOrders();
	
	List<BookOrder> getOutstandingOrders();
	
	void close();

	List<BookOrder> getAllReaderOrders(Long readerId);

	List<BookOrder> getExecutedReaderOrders(Long readerId);

	List<BookOrder> getOutstandingReaderOrders(Long readerId);
}
