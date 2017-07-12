package ua.training.controller.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.service.BookOrderService;

public class ReturnOrderToReadingRoomCommand implements Command {

	private BookOrderService bookOrderService;

	public ReturnOrderToReadingRoomCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long orderId = Long.valueOf(request.getParameter(Attribute.ID_ORDER));
		bookOrderService.backOrderToReadingRoom(orderId);
		redirectToAllOrdersPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_RETURN_ORDER_TO_READING_ROOM);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}