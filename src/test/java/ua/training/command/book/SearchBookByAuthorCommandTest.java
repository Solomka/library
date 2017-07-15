package ua.training.command.book;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
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
import ua.training.controller.command.book.SearchBookByAuthorCommand;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.service.BookService;
import ua.training.testData.BookTestData;

public class SearchBookByAuthorCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookService bookService;
	private SearchBookByAuthorCommand searchBookByAuthorCommand;

	private List<Book> books = BookTestData.generateBooksListWithSameAuthor();

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookService = mock(BookService.class);		
	}
	
	private void initSearchBookByAuthorCommand(){
		searchBookByAuthorCommand = new SearchBookByAuthorCommand(bookService);		
	}

	private void initSearchBookWithAuthorsByAuthorMethodStubbing() {
		when(bookService.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);
	}

	private void initObjectsMethodsStubbingForValidInput() {
		when(httpServletRequest.getParameter(Attribute.AUTHOR)).thenReturn("Test Author");
	}

	private void initObjectsMethodsStubbingForInValidInput() {
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.AUTHOR)).thenReturn("<Test Author>");
	}

	@Test
	// @Ignore
	public void testSearchBookByAuthorValidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initSearchBookByAuthorCommand();
		initSearchBookWithAuthorsByAuthorMethodStubbing();
		initObjectsMethodsStubbingForValidInput();

		String commandExecutionResult = searchBookByAuthorCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = Page.ALL_BOOKS_VIEW;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService).searchBookWithAuthorsByAuthor(anyString());
		verify(httpServletRequest).setAttribute(anyString(), eq(books));
	}

	@Test
	// @Ignore
	public void testSearchBookByAuthorInValidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initSearchBookByAuthorCommand();
		initSearchBookWithAuthorsByAuthorMethodStubbing();
		initObjectsMethodsStubbingForInValidInput();

		String commandExecutionResult = searchBookByAuthorCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService, never()).searchBookWithAuthorsByAuthor(anyString());
	}
}
