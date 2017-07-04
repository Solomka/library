package ua.training.controller.command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.controller.constants.ServletPath;
import ua.training.controller.dto.CredentialsDto;
import ua.training.locale.Message;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;
import ua.training.validator.entity.BookValidator;
import ua.training.validator.entity.CredentialsValidator;

public class PostAddBookCommand implements Command {
	
	private BookService bookService;
	
	private List<String> errors = new ArrayList<>();
		
	public PostAddBookCommand(BookService bookService){
		this.bookService = bookService;		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		validateUserInput(request);

		if (errors.isEmpty()) {
			bookService.createBook(new Book());
			return ServletPath.ALL_BOOKS;
			}

		addRequestAtrributes(request);
		return Page.ADD_BOOK_VIEW;
	}
	
	private void validateUserInput(HttpServletRequest request) {
		/*
		bookDto = new BookDto();
		System.out.println("ISBN PARAM: " + request.getParameter("isbn"));
		bookDto.setIsbn(request.getParameter("isbn"));
		bookDto.setTitle(request.getParameter("title"));
		bookDto.setPublisher(request.getParameter("publisher"));
		bookDto.setImprintDate(LocalDate.parse(request.getParameter("imprintDate")));
		
		errors = BookValidator.getInstance().validate(bookDto);
		*/
	} 

	private void addRequestAtrributes(HttpServletRequest request) {
		request.setAttribute(Attribute.ERRORS, errors);	
		
	}

	

}