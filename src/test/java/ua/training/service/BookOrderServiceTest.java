package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import ua.training.dao.BookInstanceDao;
import ua.training.dao.BookOrderDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.exception.ServiceException;
import ua.training.testData.BookOrderTestData;

public class BookOrderServiceTest {

	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private BookOrderDao bookOrderDao;
	private BookInstanceDao bookInstanceDao;
	private BookOrderService bookOrderService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		bookOrderDao = mock(BookOrderDao.class);
		bookInstanceDao = mock(BookInstanceDao.class);
	}

	private void initBookOrderService() {
		bookOrderService = new BookOrderService(daoFactory);
	}

	private void initBookOrderDaoCreationStubbing() {
		when(daoFactory.createBookOrderDao()).thenReturn(bookOrderDao);
	}

	private void initBookOrderDaoCreationWithDaoConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookOrderDao(daoConnection)).thenReturn(bookOrderDao);
	}

	private void initBookInstanceDaoCreationStubbing() {
		when(daoFactory.createBookInstancesDao(daoConnection)).thenReturn(bookInstanceDao);
	}

	@Test
	// @Ignore
	public void testGetAllOrders() {
		List<BookOrder> bookOrders = BookOrderTestData.generateUnfulfilledBookOrdersList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.getAll()).thenReturn(bookOrders);

		List<BookOrder> actualBookOrders = bookOrderService.getAllOrders();

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).getAll();
	}

	@Test
	// @Ignore
	public void testGetNotReturnedReaderOrders() {
		List<BookOrder> bookOrders = BookOrderTestData.generateUnfulfilledBookOrdersList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.getNotReturnedReaderOrders(anyLong())).thenReturn(bookOrders);

		Long readerId = new Long(2);
		List<BookOrder> actualBookOrders = bookOrderService.getNotReturnedReaderOrders(readerId);

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).getNotReturnedReaderOrders(readerId);
	}

	@Test
	// @Ignore
	public void testGetUnfulfilledOrders() {
		List<BookOrder> bookOrders = BookOrderTestData.generateUnfulfilledBookOrdersList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.getUnfulfilledOrders()).thenReturn(bookOrders);

		List<BookOrder> actualBookOrders = bookOrderService.getUnfulfilledOrders();

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).getUnfulfilledOrders();
	}

	@Test
	// @Ignore
	public void testGetOutstandingOrders() {
		List<BookOrder> bookOrders = BookOrderTestData.generateOutstandingBookOrdersList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.getOutstandingOrders()).thenReturn(bookOrders);

		List<BookOrder> actualBookOrders = bookOrderService.getOutstandingOrders();

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).getOutstandingOrders();
	}

	@Test
	// @Ignore
	public void testSearchNotReturnedOrdersByReaderCardNumber() {
		List<BookOrder> bookOrders = BookOrderTestData.generateUnfulfilledBookOrdersList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.searchNotReturnedOrdersByReaderCardNumber(anyString())).thenReturn(bookOrders);

		String readerCardNumber = "KB67890987567";
		List<BookOrder> actualBookOrders = bookOrderService.searchNotReturnedOrdersByReaderCardNumber(readerCardNumber);

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).searchNotReturnedOrdersByReaderCardNumber(readerCardNumber);
	}

	@Test
	// @Ignore
	public void testGetOrdersForReadingRoomReturn() {
		List<BookOrder> bookOrders = BookOrderTestData.generateOrdersForReadingRoomReturnList();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationStubbing();
		when(bookOrderDao.getOrdersForReadingRoomReturn()).thenReturn(bookOrders);

		List<BookOrder> actualBookOrders = bookOrderService.getOrdersForReadingRoomReturn();

		assertEquals(bookOrders, actualBookOrders);
		verify(daoFactory).createBookOrderDao();
		verify(bookOrderDao).getOrdersForReadingRoomReturn();
	}

	@Test
	// @Ignore
	public void testCreateOrderSuccess() {
		BookOrder order = BookOrderTestData.generateOrderForCreation();
		Optional<BookInstance> bookInstance = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstance);
		when(bookOrderDao.countUnreturnedBookInstancesNumber(anyLong())).thenReturn(3);
		when(bookOrderDao.countUnreturnedSameBookInstancesNumber(anyLong(), anyLong())).thenReturn(0);

		BookOrderService bookServiceSpy = spy(bookOrderService);
		spyBookOrderServiceGetCurrentLocalDate(bookServiceSpy);

		Long readerId = new Long(1);
		Long bookInstanceId = new Long(1);
		bookServiceSpy.createOrder(readerId, bookInstanceId);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookOrderDao(daoConnection);
		verify(daoFactory).createBookInstancesDao(daoConnection);
		verify(bookOrderDao).countUnreturnedBookInstancesNumber(readerId);
		verify(bookOrderDao).countUnreturnedSameBookInstancesNumber(readerId, bookInstanceId);
		verify(bookInstanceDao).getById(bookInstanceId);
		verify(bookOrderDao).create(order);
		verify(bookInstanceDao).update(bookInstance.get());
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testCreateOrderUnreturnedBookInstancesNumberFailure() {
		Optional<BookInstance> bookInstance = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstance);
		when(bookOrderDao.countUnreturnedBookInstancesNumber(anyLong())).thenReturn(5);
		when(bookOrderDao.countUnreturnedSameBookInstancesNumber(anyLong(), anyLong())).thenReturn(0);

		BookOrderService bookServiceSpy = spy(bookOrderService);
		spyBookOrderServiceGetCurrentLocalDate(bookServiceSpy);

		Long readerId = new Long(1);
		Long bookInstanceId = new Long(1);
		bookServiceSpy.createOrder(readerId, bookInstanceId);
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testCreateOrderUnreturnedSameBookInstancesNumberFailure() {
		Optional<BookInstance> bookInstance = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstance);
		when(bookOrderDao.countUnreturnedBookInstancesNumber(anyLong())).thenReturn(3);
		when(bookOrderDao.countUnreturnedSameBookInstancesNumber(anyLong(), anyLong())).thenReturn(1);

		BookOrderService bookServiceSpy = spy(bookOrderService);
		spyBookOrderServiceGetCurrentLocalDate(bookServiceSpy);

		Long readerId = new Long(1);
		Long bookInstanceId = new Long(1);
		bookServiceSpy.createOrder(readerId, bookInstanceId);
	}

	private void spyBookOrderServiceGetCurrentLocalDate(BookOrderService bookServiceSpy) {
		when(bookServiceSpy.getCurrentLocalDate()).thenReturn(LocalDate.of(2017, 06, 17));
	}

	@Test
	// @Ignore
	public void testFulfilOrderSuccess() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderWithCreationDate());
		BookOrder order = orderOptional.get();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();

		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(1);
		Long librarianId = new Long(2);
		bookOrderService.fulfilOrder(orderId, librarianId);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookOrderDao(daoConnection);
		verify(bookOrderDao).getById(orderId);
		verify(bookOrderDao).update(order);
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testFulfilOrderAlreadyFulfilledFailure() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderWithFulfilmentDate());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();

		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(1);
		Long librarianId = new Long(2);
		bookOrderService.fulfilOrder(orderId, librarianId);
	}

	@Test
	// @Ignore
	public void testIssueOrderSuccess() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderWithFulfilmentDate());
		BookOrder order = orderOptional.get();

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();

		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.issueOrder(orderId);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookOrderDao(daoConnection);
		verify(bookOrderDao).getById(orderId);
		verify(bookOrderDao).update(order);

	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testIssueOrderUnfulfilledOrderFailure() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderWithCreationDate());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();

		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.issueOrder(orderId);
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testIssueOrderAlreadyIssuedOrderFailure() {
		Optional<BookOrder> orderOptional = Optional
				.of(BookOrderTestData.generateOrderWithFulfilmentPickUpReturnDateAndSubcriptionBook());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();

		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.issueOrder(orderId);
	}

	@Test
	// @Ignore
	public void testReturnOrderSuccess() {
		Optional<BookOrder> orderOptional = Optional
				.of(BookOrderTestData.generateOrderWithFulfilmentPickUpReturnDateAndSubcriptionBook());
		BookOrder order = orderOptional.get();
		Optional<BookInstance> bookInstanceOptional = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstanceOptional);
		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.returnOrder(orderId);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookOrderDao(daoConnection);
		verify(daoFactory).createBookInstancesDao(daoConnection);
		verify(bookOrderDao).getById(orderId);
		verify(bookOrderDao).update(order);
		verify(bookInstanceDao).update(bookInstanceOptional.get());
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testReturnOrderUnfulfilledOrderFailure() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderForCreation());
		Optional<BookInstance> bookInstanceOptional = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstanceOptional);
		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.returnOrder(orderId);
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testReturnOrderUnissuedOrderFailure() {
		Optional<BookOrder> orderOptional = Optional.of(BookOrderTestData.generateOrderWithFulfilmentDate());
		Optional<BookInstance> bookInstanceOptional = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstanceOptional);
		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.returnOrder(orderId);
	}

	@Test(expected = ServiceException.class)
	// @Ignore
	public void testReturnOrderAlreadyReturnedFailure() {
		Optional<BookOrder> orderOptional = Optional
				.of(BookOrderTestData.generateOrderWithFulfilmentPickUpReturnActualReturnDateAndSubcriptionBook());
		Optional<BookInstance> bookInstanceOptional = Optional
				.of(BookOrderTestData.generateReadingRoomAvailableOrderBookInstance());

		initObjectsMocking();
		initBookOrderService();
		initBookOrderDaoCreationWithDaoConnectionStubbing();
		initBookInstanceDaoCreationStubbing();

		when(bookInstanceDao.getById(anyLong())).thenReturn(bookInstanceOptional);
		when(bookOrderDao.getById(anyLong())).thenReturn(orderOptional);

		Long orderId = new Long(2);
		bookOrderService.returnOrder(orderId);
	}
}
