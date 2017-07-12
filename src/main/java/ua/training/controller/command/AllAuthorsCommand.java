package ua.training.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.entity.Author;
import ua.training.service.AuthorService;

public class AllAuthorsCommand implements Command {

	private AuthorService authorService;

	public AllAuthorsCommand(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Author> authors = authorService.getAllAuthors();
		request.setAttribute(Attribute.AUTHORS, authors);
		return Page.ALL_AUTHORS_VIEW;
	}
}
