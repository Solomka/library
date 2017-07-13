package ua.training.controller.command.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.service.AuthorService;

public class GetAddBookCommand implements Command {

	private AuthorService authorService;

	public GetAddBookCommand(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Author> authors = authorService.getAllAuthors();

		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());
		request.setAttribute(Attribute.AUTHORS, authors);

		return Page.ADD_BOOK_VIEW;
	}
}
