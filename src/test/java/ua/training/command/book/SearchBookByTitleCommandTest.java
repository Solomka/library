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
import ua.training.controller.command.book.SearchBookByTitleCommand;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Book;
import ua.training.service.BookService;
import ua.training.testData.BookTestData;

public class SearchBookByTitleCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookService bookService;
	private SearchBookByTitleCommand searchBookByTitleCommand;

	private List<Book> books = BookTestData.generateBooksListWithAuthors();

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookService = mock(BookService.class);
	}

	private void initSearchBookByTitleCommand() {
		searchBookByTitleCommand = new SearchBookByTitleCommand(bookService);
	}

	private void initSearchBookWithAuthorsByTitleMethodStubbing() {
		when(bookService.searchBookWithAuthorsByTitle(anyString())).thenReturn(books);
	}

	private void initObjectsMethodsStubbingForValidInput() {
		when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("Test Title");
	}

	private void initObjectsMethodsStubbingForInValidInput() {
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("<Test Title>");
	}

	@Test
	// @Ignore
	public void testSearchBookByTitleValidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initSearchBookByTitleCommand();
		initSearchBookWithAuthorsByTitleMethodStubbing();
		initObjectsMethodsStubbingForValidInput();

		String commandExecutionResult = searchBookByTitleCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = Page.ALL_BOOKS_VIEW;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService).searchBookWithAuthorsByTitle(anyString());
		verify(httpServletRequest).setAttribute(anyString(), eq(books));
	}

	@Test
	// @Ignore
	public void testSearchBookByTitleInValidInputCommand() throws ServletException, IOException {
		initObjectsMocking();
		initSearchBookByTitleCommand();
		initSearchBookWithAuthorsByTitleMethodStubbing();
		initObjectsMethodsStubbingForInValidInput();

		String commandExecutionResult = searchBookByTitleCommand.execute(httpServletRequest, httpServletResponse);
		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = commandExecutionResult;

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService, never()).searchBookWithAuthorsByTitle(anyString());
	}
}
