package ua.training.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.utils.CommandKeyGenerator;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.exception.ServiceException;

/**
 * Application HTTP Front Servlet
 */

@WebServlet(urlPatterns = { "/controller/*" }, loadOnStartup = 1)
public class FrontController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;

	public FrontController() {

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

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		String commandKey = CommandKeyGenerator.generateCommandKeyFromRequest(request);
		Command command = CommandFactory.getCommand(commandKey);
		try {
			String commandResultedResource = command.execute(request, response);
			forwardToCommandResultedPage(httpWrapper, commandResultedResource);
		} catch (ServiceException ex) {
			redirecToHomePageWithErrorMessage(httpWrapper, ex);
		}

	}

	private void forwardToCommandResultedPage(HttpWrapper httpWrapper, String resultRedirectResource)
			throws ServletException, IOException {
		if (!resultRedirectResource.contains(RedirectionManager.REDIRECTION)) {
			httpWrapper.getRequest().getRequestDispatcher(resultRedirectResource).forward(httpWrapper.getRequest(),
					httpWrapper.getResponse());
		}
	}

	private void redirecToHomePageWithErrorMessage(HttpWrapper httpWrapper, ServiceException ex) throws IOException {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.ERROR, ex.getMessage());
		RedirectionManager.redirectWithParams(httpWrapper, ServletPath.HOME, urlParams);
	}
}
