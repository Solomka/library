package ua.training.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Application HTTP Front Servlet
 */

@WebServlet(urlPatterns={"/controller/*"}) 
public class FrontController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;

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
		System.out.println("REQUEST: " + request.getRequestURL().toString());
		
		String requestResultPage = null;

		requestResultPage = CommandFactory.getCommand(request).execute(request, response);
		request.getRequestDispatcher(requestResultPage).forward(request, response);
		//response.sendRedirect(requestResultPage);
	}

}
