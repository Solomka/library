package ua.training.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.SearchBookByAuthorCommand;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.service.BookService;

public class SearchBookByAuthorCommandTest {
	@Test
	// @Ignore
	public void testSearchBookByAuthorValidInputCommand() throws ServletException, IOException {

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		BookService bookService = mock(BookService.class);

		List<Book> books = Arrays.asList(new Book[] {
				new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("2222222222222").setTitle("Test Title2").setPublisher("Test Publisher2")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("3333333333333").setTitle("Test Title3").setPublisher("Test Publisher3")
						.setAvailability(Availability.SUBSCRIPTION).build() });

		when(bookService.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);
		when(httpServletRequest.getParameter(Attribute.AUTHOR)).thenReturn("Test Author");

		SearchBookByAuthorCommand searchBookByAuthorCommand = new SearchBookByAuthorCommand(bookService);

		String expectedResultedResource = Page.ALL_BOOKS_VIEW;
		String actualResultedResource = searchBookByAuthorCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(bookService).searchBookWithAuthorsByAuthor(anyString());
		verify(httpServletRequest).getParameter(Attribute.AUTHOR);
		verify(httpServletRequest).setAttribute(anyString(), eq(books));
	}

	@Test
	// @Ignore
	public void testSearchBookByAuthorInValidInputCommand() throws ServletException, IOException {

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		BookService bookService = mock(BookService.class);

		List<Book> books = Arrays.asList(new Book[] {
				new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("2222222222222").setTitle("Test Title2").setPublisher("Test Publisher2")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("3333333333333").setTitle("Test Title3").setPublisher("Test Publisher3")
						.setAvailability(Availability.SUBSCRIPTION).build() });

		when(bookService.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.AUTHOR)).thenReturn("<Test Author>");

		SearchBookByAuthorCommand searchBookByAuthorCommand = new SearchBookByAuthorCommand(bookService);

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = searchBookByAuthorCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(bookService, never()).searchBookWithAuthorsByAuthor(anyString());
	}
}
