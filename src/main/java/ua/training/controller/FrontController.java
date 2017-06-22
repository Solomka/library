package ua.training.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.controller.page.PageResolver;

/**
 * Application HTTP Front Servlet
 */

public class FrontController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;

	private PageResolver pageResolver = new PageResolver("./WEB-INF/views/", ".jsp");

	public FrontController() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestResultPage = null;

		requestResultPage = CommandFactory.getCommand(request).execute(request, response);
		request.getRequestDispatcher(pageResolver.resolve(requestResultPage)).forward(request, response);
	}

}
