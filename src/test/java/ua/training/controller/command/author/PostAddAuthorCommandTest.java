package ua.training.controller.command.author;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Author;
import ua.training.service.AuthorService;
import ua.training.testData.AuthorTestData;

public class PostAddAuthorCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private AuthorService authorService;
	private PostAddAuthorCommand postAddAuthorCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		authorService = mock(AuthorService.class);
	}

	private void initPostAddAuthorCommand() {
		postAddAuthorCommand = new PostAddAuthorCommand(authorService);
	}

	private void initObjectsMethodsStubbingForValidInput() {
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("Daniel");
		when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn("Keyes");
		when(httpServletRequest.getParameter(Attribute.COUNTRY)).thenReturn("America");
	}

	private void initObjectsMethodsStubbingForInvalidInput() {
		when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.COUNTRY)).thenReturn("");
	}

	@Test
	//@Ignore
	public void testAddAuthorValidInput() throws ServletException, IOException {
		Author author = AuthorTestData.generateAuthor();

		initObjectsMocking();
		initPostAddAuthorCommand();
		initObjectsMethodsStubbingForValidInput();

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = postAddAuthorCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(authorService).createAuthor(author);
	}

	@Test
	//@Ignore
	public void testAddAuthorInvalidInput() throws ServletException, IOException {
		Author author = AuthorTestData.generateAuthor();

		initObjectsMocking();
		initPostAddAuthorCommand();
		initObjectsMethodsStubbingForInvalidInput();

		String expectedResultedResource = Page.ADD_AUTHOR_VIEW;
		String actualResultedResource = postAddAuthorCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(httpServletRequest).setAttribute(eq(Attribute.AUTHOR), anyObject());
		verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS), anyListOf(String.class));
		verify(authorService, never()).createAuthor(author);
	}
}
