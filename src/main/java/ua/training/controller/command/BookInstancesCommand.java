package ua.training.controller.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.session.SessionManager;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.model.entity.Book;
import ua.training.model.entity.Role;
import ua.training.model.service.BookService;

public class BookInstancesCommand implements Command {

	private BookService bookService;

	public BookInstancesCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long bookId = Long.parseLong(request.getParameter(Attribute.ID_BOOK));
		Optional<Book> book = getBookDependingOnUserRole(request.getSession(), bookId);

		if (!book.isPresent()) {
			redirectToAllBooksPageWithErrorMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.BOOK, book.get());
		return Page.BOOK_INSTANCES_VIEW;

	}

	private Optional<Book> getBookDependingOnUserRole(HttpSession session, Long bookId) {
		if (!SessionManager.isUserLoggedIn(session) || SessionManager.getUserFromSession(session).getRole().equals(Role.LIBRARIAN)) {
			return bookService.getBookWithAuthorsAndInstances(bookId);
		}
		return bookService.getBookWithAuthorsAndAvailableInstances(bookId);
	}
	
	private void redirectToAllBooksPageWithErrorMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.NO_AVAILABLE_BOOK_INSTANCES);
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
	}

	
}
