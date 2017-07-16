package ua.training.controller.command.bookOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Page;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.BookOrder;
import ua.training.service.BookOrderService;
import ua.training.testData.BookOrderTestData;

public class UnfulfilledOrdersCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookOrderService bookOrderService;
	private UnfulfilledOrdersCommand unfulfilledOrdersCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookOrderService = mock(BookOrderService.class);
	}

	private void initUnfulfilledOrdersCommand() {
		unfulfilledOrdersCommand = new UnfulfilledOrdersCommand(bookOrderService);
	}

	@Test
	// @Ignore
	public void testGetUnfulfilledOrdersNotEmptyList() throws ServletException, IOException {
		List<BookOrder> orders = BookOrderTestData.generateUnfulfilledBookOrdersList();

		initObjectsMocking();
		initUnfulfilledOrdersCommand();
		when(bookOrderService.getUnfulfilledOrders()).thenReturn(orders);

		String expectedResultedResource = Page.ALL_ORDERS_VIEW;
		String actualResultedResource = unfulfilledOrdersCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookOrderService).getUnfulfilledOrders();
		verify(httpServletRequest).setAttribute(anyString(), eq(orders));
	}

	@Test
	// @Ignore
	public void testGetUnfulfilledOrdersEmptyList() throws ServletException, IOException {
		List<BookOrder> orders = new ArrayList<>();

		initObjectsMocking();
		initUnfulfilledOrdersCommand();
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(bookOrderService.getUnfulfilledOrders()).thenReturn(orders);

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = unfulfilledOrdersCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookOrderService).getUnfulfilledOrders();
	}
}
