package ua.training.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;
import ua.training.model.entity.Availability;

public class GetAddBookCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		request.setAttribute(Attribute.AVAILABILITIES, Availability.getValues());
		return Page.ADD_BOOK_VIEW;
	}

}
