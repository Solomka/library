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
import ua.training.controller.utils.UrlParamMessageGenerator;
import ua.training.locale.Message;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;
import ua.training.validator.field.FieldValidator;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class SearchBookByTitleCommand implements Command {

	private BookService bookService;

	public SearchBookByTitleCommand(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter(Attribute.TITLE);
		List<String> errors = validateUserInput(title);

		if (!errors.isEmpty()) {
			return request.getContextPath() + request.getServletPath() + ServletPath.ALL_BOOKS
					+ UrlParamMessageGenerator.getMessageURLParam(Attribute.ERROR, errors.get(0));
		}

		List<Book> books = bookService.searchBookByTitle(title);
		
		if (books.isEmpty()) {
			return request.getContextPath() + request.getServletPath() + ServletPath.ALL_BOOKS
					+ UrlParamMessageGenerator.getMessageURLParam(Attribute.ERROR, Message.BOOK_IS_NOT_FOUND);

		}

		request.setAttribute(Attribute.BOOKS, books);
		return Page.ALL_BOOKS_VIEW;

	}

	private List<String> validateUserInput(String title) {
		List<String> errors = new ArrayList<>();

		FieldValidator fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.TITLE, title, errors);
		return errors;
	}

}
