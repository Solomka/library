package ua.training.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.model.entity.Author;
import ua.training.model.entity.Availability;
import ua.training.model.entity.Book;
import ua.training.model.service.AuthorService;
import ua.training.model.service.BookService;
import ua.training.validator.entity.BookValidator;

public class PostAddBookCommand implements Command {

	private BookService bookService;
	private AuthorService authorService;

	private List<Author> authors = new ArrayList<>();
	private List<String> errors = new ArrayList<>();
	private Book book;

	public PostAddBookCommand(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		validateUserInput(request);

		if (errors.isEmpty()) {
			bookService.createBook(book);
			RedirectionManager.redirectWithParamMessage(request, response, ServletPath.ALL_BOOKS, Attribute.SUCCESS,
					Message.SUCCESS_BOOK_ADDITION);
			return RedirectionManager.REDIRECTION;
		}

		addRequestAtrributes(request);
		return Page.ADD_BOOK_VIEW;
	}

	private void validateUserInput(HttpServletRequest request) {

		List<Author> bookAuthors = getBookAuthors(request);

		book = new Book.Builder().setIsbn(request.getParameter(Attribute.ISBN))
				.setTitle(request.getParameter(Attribute.TITLE)).setPublisher(request.getParameter(Attribute.PUBLISHER))
				.setAvailability(Availability.forValue(request.getParameter(Attribute.AVAILABILITY)))
				.setAuthors(bookAuthors).build();

		errors = BookValidator.getInstance().validate(book);

	}

	private List<Author> getBookAuthors(HttpServletRequest request) {
		String[] authorsIds = request.getParameterValues(Attribute.AUTHORS);
		List<Author> bookAuthors = new ArrayList<>();

		for (String authorId : authorsIds) {
			bookAuthors.add(new Author.Builder().setId(new Long(authorId)).build());
		}

		return bookAuthors;
	}

	private void addRequestAtrributes(HttpServletRequest request) {
		authors = authorService.getAllAuthors();

		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());
		request.setAttribute(Attribute.AUTHORS, authors);
		request.setAttribute(Attribute.BOOK, book);
		request.setAttribute(Attribute.ERRORS, errors);
	}

}
