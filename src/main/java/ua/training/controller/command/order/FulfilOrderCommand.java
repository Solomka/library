package ua.training.controller.command.order;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.locale.Message;
import ua.training.service.BookOrderService;

public class FulfilOrderCommand implements Command {

	private BookOrderService bookOrderService;

	public FulfilOrderCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long orderId = Long.valueOf(request.getParameter(Attribute.ID_ORDER));
		Long librarianId = SessionManager.getInstance().getUserFromSession(request.getSession()).getId();
		bookOrderService.fulfilOrder(orderId, librarianId);
		redirectToAllOrdersPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_FULFILMENT);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}
