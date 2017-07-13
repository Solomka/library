package ua.training.dao;

import java.util.List;

import ua.training.entity.BookOrder;

public interface BookOrderDao extends GenericDao<BookOrder, Long>, AutoCloseable {
	
	List<BookOrder> getUnexecutedOrders();
	
	List<BookOrder> getOutstandingOrders();
	
	List<BookOrder> getNotReturnedReaderOrders(Long readerId);

	List<BookOrder> searchNotReturnedOrdersByReaderCardNumber(String readerCardNumber);

	List<BookOrder> getOrdersForReadingRoomReturn();
	
	void close();
}
