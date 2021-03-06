package ua.training.controller.command.book;

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
import ua.training.dto.BookDto;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.locale.Message;
import ua.training.service.AuthorService;
import ua.training.service.BookService;
import ua.training.validator.entity.BookValidator;

public class PostAddBookCommand implements Command {

	private BookService bookService;
	private AuthorService authorService;

	public PostAddBookCommand(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BookDto book = getUserInput(request);
		List<String> errors = validateUserInput(book);

		if (errors.isEmpty()) {
			bookService.createBook(book);
			redirectToAllBooksPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		addRequesAttributes(request, book, errors);
		return Page.ADD_BOOK_VIEW;
	}

	private BookDto getUserInput(HttpServletRequest request) {
		return new BookDto.Builder().setIsbn(request.getParameter(Attribute.ISBN))
				.setTitle(request.getParameter(Attribute.TITLE)).setPublisher(request.getParameter(Attribute.PUBLISHER))
				.setAvailability(Availability.forValue(request.getParameter(Attribute.AVAILABILITY)))
				.setAuthorsIds(request.getParameterValues(Attribute.AUTHORS)).build();
	}

	private List<String> validateUserInput(BookDto book) {
		return BookValidator.getInstance().validate(book);
	}

	private void redirectToAllBooksPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_BOOK_ADDITION);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_BOOKS, urlParams);
	}

	private void addRequesAttributes(HttpServletRequest request, BookDto book, List<String> errors) {
		List<Author> authors = authorService.getAllAuthors();
		request.setAttribute(Attribute.AUTHORS, authors);
		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());

		request.setAttribute(Attribute.BOOK, book);
		request.setAttribute(Attribute.ERRORS, errors);
	}
}
