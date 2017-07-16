package ua.training.controller.command.auth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.utils.RedirectionManager;
import ua.training.dto.CredentialsDto;
import ua.training.entity.User;
import ua.training.service.UserService;
import ua.training.testData.UserTestData;

public class PostLoginCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private HttpSession httpSession;
	private UserService userService;
	private PostLoginCommand postLoginCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		httpSession = mock(HttpSession.class);
		userService = mock(UserService.class);
	}

	private void initPostLoginCommand() {
		postLoginCommand = new PostLoginCommand(userService);
	}

	private void initSessionCreationStubbing() {
		when(httpServletRequest.getSession()).thenReturn(httpSession);
	}

	private void initObjectsMethodsStubbingForNotLoggedInValidInput() {
		when(httpSession.getAttribute(Attribute.USER)).thenReturn(null);
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("anna@gmail.com");
		when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("library7");
	}

	private void initObjectsMethodsStubbingForNotLoggedInInvalidInput() {
		when(httpSession.getAttribute(Attribute.USER)).thenReturn(null);
		when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("");
	}

	private void initObjectsMethodsStubbingForLoggedIn() {
		User user = UserTestData.generateReaderAsUser();
		when(httpSession.getAttribute(Attribute.USER)).thenReturn(user);
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
	}

	@Test
	// @Ignore
	public void testLoginNotLoggedInValidInputUserIsPresent() throws ServletException, IOException {
		Optional<User> user = UserTestData.generateReaderAsUserOptional();
		CredentialsDto credentialsDto = UserTestData.generateCredentialsDtoWithValidPass();

		initObjectsMocking();
		initPostLoginCommand();
		initSessionCreationStubbing();
		initObjectsMethodsStubbingForNotLoggedInValidInput();
		when(userService.getUserByEmail(anyObject())).thenReturn(user);

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(userService).getUserByEmail(credentialsDto);
	}

	@Test
	// @Ignore
	public void testLoginNotLoggedInValidInputUserIsNotPresent() throws ServletException, IOException {
		Optional<User> user = Optional.empty();
		CredentialsDto credentialsDto = UserTestData.generateCredentialsDtoWithValidPass();

		initObjectsMocking();
		initPostLoginCommand();
		initSessionCreationStubbing();
		initObjectsMethodsStubbingForNotLoggedInValidInput();
		when(userService.getUserByEmail(anyObject())).thenReturn(user);

		String expectedResultedResource = Page.LOGIN_VIEW;
		String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(userService).getUserByEmail(credentialsDto);
		verify(httpServletRequest).setAttribute(Attribute.LOGIN_USER, credentialsDto);
		verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS), anyListOf(String.class));
	}

	@Test
	// @Ignore
	public void testLoginNotLoggedInInvalidInput() throws ServletException, IOException {

		initObjectsMocking();
		initPostLoginCommand();
		initSessionCreationStubbing();
		initObjectsMethodsStubbingForNotLoggedInInvalidInput();

		String expectedResultedResource = Page.LOGIN_VIEW;
		String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(userService, never()).getUserByEmail(anyObject());
		verify(httpServletRequest).setAttribute(eq(Attribute.LOGIN_USER), anyObject());
		verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS), anyListOf(String.class));
	}

	@Test
	// @Ignore
	public void testLoginLoggedIn() throws ServletException, IOException {

		initObjectsMocking();
		initPostLoginCommand();
		initSessionCreationStubbing();
		initObjectsMethodsStubbingForLoggedIn();

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = postLoginCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(userService, never()).getUserByEmail(anyObject());
	}
}
