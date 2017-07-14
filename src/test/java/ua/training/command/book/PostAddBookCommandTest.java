package ua.training.command.book;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.book.PostAddBookCommand;
import ua.training.controller.utils.RedirectionManager;
import ua.training.dto.BookDto;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.service.AuthorService;
import ua.training.service.BookService;
import ua.training.testData.BookTestData;

public class PostAddBookCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookService bookService;
	private AuthorService authorService;
	private PostAddBookCommand postAddBookCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookService = mock(BookService.class);
		authorService = mock(AuthorService.class);
		postAddBookCommand = new PostAddBookCommand(bookService, authorService);
	}

	private void initObjectsMethodsStubbingForValidInput() {
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.ISBN)).thenReturn("1111111111111");
		when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("Test Title1");
		when(httpServletRequest.getParameter(Attribute.PUBLISHER)).thenReturn("Test Publisher1");
		when(httpServletRequest.getParameter(Attribute.AVAILABILITY)).thenReturn("subscription");
		when(httpServletRequest.getParameterValues(Attribute.AUTHORS)).thenReturn(new String[] { "1", "2" });
	}

	private void initObjectsMethodsStubbingForInvalidEmptyInput() {
		when(httpServletRequest.getParameter(Attribute.ISBN)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.PUBLISHER)).thenReturn("");
		when(httpServletRequest.getParameter(Attribute.AVAILABILITY)).thenReturn("subscription");
		when(httpServletRequest.getParameterValues(Attribute.AUTHORS)).thenReturn(null);
	}

	@Test
	// @Ignore
	public void testPostAddBookValidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initObjectsMethodsStubbingForValidInput();

		BookDto bookDto = BookTestData.generateBookDtoWithAuthors();

		String commandExecutionResult = postAddBookCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService).createBook(bookDto);
	}

	@Test
	// @Ignore
	public void testPostAddBookInvalidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initObjectsMethodsStubbingForInvalidEmptyInput();

		List<Author> authors = BookTestData.generateAuthorsList();
		when(authorService.getAllAuthors()).thenReturn(authors);
		
		String commandExecutionResult = postAddBookCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = Page.ADD_BOOK_VIEW;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(httpServletRequest).setAttribute(eq(Attribute.AUTHORS), eq(authors));
		verify(httpServletRequest).setAttribute(eq(Attribute.AVAILABILITIES), eq(Availability.getValues()));
		verify(httpServletRequest).setAttribute(eq(Attribute.BOOK), anyObject());
		verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS), anyListOf(String.class));
		verify(bookService, never()).createBook(anyObject());
	}
}
