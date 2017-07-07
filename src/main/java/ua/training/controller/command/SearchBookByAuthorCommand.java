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
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class SearchBookByAuthorCommand implements Command {

	private BookService bookService;

	public SearchBookByAuthorCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String author = request.getParameter(Attribute.AUTHOR);
		List<String> errors = validateUserInput(author);

		if (!errors.isEmpty()) {
			RedirectionManager.redirectWithParamMessage(request, response, ServletPath.ALL_BOOKS, Attribute.ERROR,
					errors.get(0));
			return RedirectionManager.REDIRECTION;
		}

		List<Book> books = bookService.searchBookByAuthor(author);

		if (books.isEmpty()) {
			RedirectionManager.redirectWithParamMessage(request, response, ServletPath.ALL_BOOKS, Attribute.ERROR,
					Message.BOOK_IS_NOT_FOUND);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.BOOKS, books);
		return Page.ALL_BOOKS_VIEW;

	}

	private List<String> validateUserInput(String author) {
		List<String> errors = new ArrayList<>();

		FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.NAME, author, errors);
		return errors;
	}

}