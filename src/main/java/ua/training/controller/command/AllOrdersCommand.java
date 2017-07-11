package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.session.SessionManager;
import ua.training.entity.BookOrder;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.BookOrderService;

public class AllOrdersCommand implements Command {

	private BookOrderService bookOrderService;

	public AllOrdersCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<BookOrder> orders;
		User user = SessionManager.getUserFromSession(request.getSession());
		if (user.getRole().equals(Role.LIBRARIAN)) {
			orders = bookOrderService.getAllOrders();
		} else {
			orders = bookOrderService.getNotReturnedReaderOrders(user.getId());
		}
		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;
	}

}
