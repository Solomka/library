package ua.training.controller.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.controller.constants.Attribute;
import ua.training.controller.constants.ServletPath;
import ua.training.locale.Message;

@WebFilter(urlPatterns = { "/views/*" })
public class DirectViewAccessFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
	
		httpResponse.sendRedirect(httpRequest.getContextPath() + ServletPath.HOME + getErrorMessageURLParam());
		//httpRequest.getSession().setAttribute(Attribute.GENERAL_ERROR, Message.DIRECT_VIEW_ACCESS_ERROR);
		// chain.doFilter(request, response);
		// request.getRequestDispatcher(indexPath).forward(httpRequest,
		// httpResponse);
	}

	public void destroy() {
	}
	
	private String getErrorMessageURLParam() throws UnsupportedEncodingException{
		return "?"+ Attribute.GENERAL_ERROR + "=" + URLEncoder.encode(Message.DIRECT_VIEW_ACCESS_ERROR, "UTF-8");
	}
}
