package ua.training.controller.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Reader;
import ua.training.service.UserService;

public class AllReadersCommand implements Command {

	private UserService userService;

	public AllReadersCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Reader> readers = userService.getAllReaders();
		request.setAttribute(Attribute.READERS, readers);
		return Page.ALL_READERS_VIEW;
	}
}
