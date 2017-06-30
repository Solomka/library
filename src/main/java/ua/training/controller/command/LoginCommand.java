package ua.training.controller.command;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.training.controller.constants.Page;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

public class LoginCommand implements Command{
	
	private UserService userService = UserService.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String pass = request.getParameter("password"); 
		
		System.out.println("Login: " + login);
		Optional<User> user = userService.getUserByLogin(login);
		
		 addUserToSession(request.getSession(), user.get());

       //  Util.redirectTo(request, response, Page.INDEX);
		return Page.ALL_BOOKS;
	}
	
	 private void addUserToSession(HttpSession session, User user)
	            throws IOException {
	        session.setAttribute("user", user);
	    }

}
