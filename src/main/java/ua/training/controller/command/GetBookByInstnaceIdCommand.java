package ua.training.controller.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.locale.Message;
import ua.training.service.BookService;

public class GetBookByInstnaceIdCommand implements Command {
	private BookService bookService;

	public GetBookByInstnaceIdCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long instanceId = Long.valueOf(request.getParameter(Attribute.ID_BOOK_INSTANCE));
		Optional<Book> book = bookService.searchBookWithAuthorsByInstanceId(instanceId);

		if (!book.isPresent()) {
			redirectToAllOrdersPageWithErrorMessage(request, response);
		}

		request.setAttribute(Attribute.BOOKS, Arrays.asList(book.get()));
		return Page.ALL_BOOKS_VIEW;
	}

	private void redirectToAllOrdersPageWithErrorMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.BOOK_IS_NOT_FOUND);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}

}
