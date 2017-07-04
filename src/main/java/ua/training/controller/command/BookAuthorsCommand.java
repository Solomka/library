package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.model.entity.Author;
import ua.training.model.service.AuthorService;

public class BookAuthorsCommand implements Command {

	private AuthorService authorService;

	public BookAuthorsCommand(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long bookId = Long.parseLong(request.getParameter(Attribute.ID_BOOK));
		//System.out.println("In book authors service");
		List<Author> authors = authorService.getBookAuthors(bookId);
		request.setAttribute(Attribute.BOOK_AUTHORS, authors);

		return Page.BOOK_AUTHORS_VIEW;
	}

}
