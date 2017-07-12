package ua.training.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Page;
import ua.training.controller.command.BookInstancesCommand;
import ua.training.entity.Book;
import ua.training.service.BookService;

public class BookInstancesCommandTest {

	@Test
	// @Ignore
	public void testAllBooksCommand() throws ServletException, IOException {

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		BookService bookService = mock(BookService.class);

		Optional<Book> book = Optional.of(new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1")
				.setPublisher("Test Publisher1").build());
		String bookIdParam = Integer.toString(2);

		when(bookService.getBookWithAuthorsAndInstances(anyLong())).thenReturn(book);
		when(httpServletRequest.getParameter(anyString())).thenReturn(bookIdParam);

		BookInstancesCommand bookInstancesCommand = new BookInstancesCommand(bookService);

		String expectedResultedResource = Page.BOOK_INSTANCES_VIEW;
		String actualResultedResource = bookInstancesCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(httpServletRequest).getParameter(anyString());
		verify(bookService).getBookWithAuthorsAndInstances(anyLong());
		verify(httpServletRequest).setAttribute(anyString(), eq(book.get()));
	}

}
