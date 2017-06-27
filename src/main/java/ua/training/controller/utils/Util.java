package ua.training.controller.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Zulu Warrior on 6/2/2017.
 */
public class Util {
	/**
	 * Add next page to redirect
	 *
	 * @param request
	 * @param response
	 * @param pageToRedirect
	 * @throws IOException
	 */
	public static void redirectTo(HttpServletRequest request, HttpServletResponse response, String pageToRedirect)
			throws IOException {
		response.sendRedirect(request.getServletPath() + pageToRedirect);
	}

}
