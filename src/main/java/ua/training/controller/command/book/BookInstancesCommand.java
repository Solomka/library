package ua.training.controller.command.book;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Book;
import ua.training.entity.Role;
import ua.training.locale.Message;
import ua.training.service.BookService;

public class BookInstancesCommand implements Command {

	private BookService bookService;

	public BookInstancesCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long bookId = Long.parseLong(request.getParameter(Attribute.ID_BOOK));
		Optional<Book> book = getBookDependingOnUserRole(session, bookId);

		if (!book.isPresent()) {
			redirectToAllBooksPageWithErrorMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.BOOK, book.get());
		return Page.BOOK_INSTANCES_VIEW;
	}

	private Optional<Book> getBookDependingOnUserRole(HttpSession session, Long bookId) {
		if (!SessionManager.getInstance().isUserLoggedIn(session) || isUserLibrarian(session)) {
			return bookService.getBookWithAuthorsAndInstancesById(bookId);
		}
		return bookService.getBookWithAuthorsAndAvailableInstancesById(bookId);
	}

	private boolean isUserLibrarian(HttpSession session) {
		return SessionManager.getInstance().isUserLoggedIn(session)
				&& SessionManager.getInstance().getUserFromSession(session).getRole().equals(Role.LIBRARIAN);
	}

	private void redirectToAllBooksPageWithErrorMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, Message.NO_AVAILABLE_BOOK_INSTANCES);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
	}
}
