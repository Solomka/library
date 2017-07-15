package ua.training.controller.command.bookOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.BookOrder;
import ua.training.locale.Message;
import ua.training.service.BookOrderService;

public class OutstandingOrdersCommand implements Command {

	private BookOrderService bookOrderService;

	public OutstandingOrdersCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BookOrder> orders = bookOrderService.getOutstandingOrders();
		if (orders.isEmpty()) {
			redirectToAllOrdersPageWithErrorMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}
		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;
	}

	private void redirectToAllOrdersPageWithErrorMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.ORDERS_ARE_NOT_FOUND);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);

	}

}
