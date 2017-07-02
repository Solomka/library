package ua.training.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class RequestPathFilter implements Filter {
	
	private static String CONTROLLER_PARH = "controller";
	private static String RESOURCES_PARH = "/resources/";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("Request uri: " + req.getRequestURI());
		System.out.println("Path before: " + path);

		if (path.startsWith(RESOURCES_PARH)) {
			// Goes to default servlet
			chain.doFilter(request, response);
		} else {
			// Goes to front controller
			request.getRequestDispatcher(CONTROLLER_PARH + path).forward(request, response);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
