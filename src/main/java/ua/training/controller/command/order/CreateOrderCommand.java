package ua.training.controller.command.order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.session.SessionManager;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.BookInstance;
import ua.training.entity.BookOrder;
import ua.training.entity.Reader;
import ua.training.entity.Status;
import ua.training.locale.Message;
import ua.training.service.BookOrderService;

public class CreateOrderCommand implements Command {

	private BookOrderService bookOrderService;

	public CreateOrderCommand(BookOrderService bookOrderService) {
		this.bookOrderService = bookOrderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long readerId = Long.valueOf(SessionManager.getUserFromSession(request.getSession()).getId());
		Long bookInsatnceId = Long.valueOf(request.getParameter(Attribute.ID_BOOK_INSTANCE));
		bookOrderService.createOrder(readerId, bookInsatnceId);
		// BookOrder bookOrder = getUserInput(request);
		// bookOrderService.createOrder(bookOrder);
		redirectToAllOrdersPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;
	}

	private BookOrder getUserInput(HttpServletRequest request) {
		String bookInstanceId = request.getParameter(Attribute.ID_BOOK_INSTANCE);
		return new BookOrder.Builder()
				.setReader(new Reader.Builder()
						.setId(Long.valueOf(SessionManager.getUserFromSession(request.getSession()).getId())).build())
				.setCreationDate(LocalDate.now()).setBookInstance(new BookInstance.Builder()
						.setId(Long.valueOf(bookInstanceId)).setStatus(Status.UNAVAILABLE).build())
				.build();
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_CREATION);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.READER_ORDERS, urlParams);

	}

}