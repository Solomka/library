package ua.training.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.controller.command.AllBooksCommand;
import ua.training.controller.constants.Page;
import ua.training.model.entity.Availability;
import ua.training.model.entity.Book;
import ua.training.model.service.BookService;

public class AllBooksCommandTest {

	@Test
	//@Ignore
	public void testBookInstancesCommand() throws ServletException, IOException {

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

		when(bookService.getAllBooks()).thenReturn(books);

		AllBooksCommand allBooksCommand = new AllBooksCommand(bookService);

		String expectedResultedResource = Page.ALL_BOOKS_VIEW;
		String actualResultedResource = allBooksCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(bookService).getAllBooks();
		verify(httpServletRequest).setAttribute(anyString(), eq(books));
	}

}