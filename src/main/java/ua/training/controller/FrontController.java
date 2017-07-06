package ua.training.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.Page;

/**
 * Application HTTP Front Servlet
 */

@WebServlet(urlPatterns = { "/controller/*" }, loadOnStartup = 1)
public class FrontController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
		processRequest(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandKey = CommandKeyGenerator.generateCommandKeyFromRequest(request);
		String resultRedirectResource = CommandFactory.getCommand(commandKey).execute(request, response);
		
		if (resultRedirectResource.contains(Page.SUFFIX)) {
			request.getRequestDispatcher(resultRedirectResource).forward(request, response);
		} else {
			response.sendRedirect(resultRedirectResource);
		}

	}

}
