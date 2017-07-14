package ua.training.command.book;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Page;
import ua.training.controller.command.book.AllBooksCommand;
import ua.training.entity.Book;
import ua.training.service.BookService;
import ua.training.testData.BookTestData;

public class AllBooksCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookService bookService;
	private AllBooksCommand allBooksCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookService = mock(BookService.class);
		allBooksCommand = new AllBooksCommand(bookService);
	}

	@Test
	// @Ignore
	public void testAlBooksCommand() throws ServletException, IOException {
		List<Book> books = BookTestData.generateBooksListWithAuthors();

		initObjectsMocking();
		when(bookService.getAllBooksWithAuthors()).thenReturn(books);

		String expectedResultedResource = Page.ALL_BOOKS_VIEW;
		String actualResultedResource = allBooksCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResultedResource, actualResultedResource);
		verify(bookService).getAllBooksWithAuthors();
		verify(httpServletRequest).setAttribute(anyString(), eq(books));
	}

}
