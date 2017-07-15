package ua.training.dao;

import java.util.List;

import ua.training.entity.BookOrder;

public interface BookOrderDao extends GenericDao<BookOrder, Long>, AutoCloseable {
	
	List<BookOrder> getNotReturnedReaderOrders(Long readerId);
	
	int countUnreturnedBookInstancesNumber(Long readerId);
	
	int countUnreturnedSameBookInstancesNumber(Long readerId, Long bookInstanceId);
	
	List<BookOrder> getUnexecutedOrders();
	
	List<BookOrder> getOutstandingOrders();	

	List<BookOrder> searchNotReturnedOrdersByReaderCardNumber(String readerCardNumber);

	List<BookOrder> getOrdersForReadingRoomReturn();
	
	void close();	
}
