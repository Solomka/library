package ua.training.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
import ua.training.controller.command.book.PostAddBookCommand;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.service.AuthorService;
import ua.training.service.BookService;

public class PostAddBookCommandTest {

	@Test
	// @Ignore
	public void testPostAddBookValidInputCommand() throws ServletException, IOException {

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		BookService bookService = mock(BookService.class);
		AuthorService authorService = mock(AuthorService.class);

		List<Author> authors = Arrays.asList(new Author[] { new Author.Builder().setId(new Long("1")).build(),
				new Author.Builder().setId(new Long("2")).build(), new Author.Builder().setId(new Long("3")).build() });

		Book book = new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
				.setAvailability(Availability.SUBSCRIPTION).setAuthors(authors).build();

		String[] authorsIds = new String[] { "1", "2", "3" };
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.ISBN)).thenReturn("1111111111111");
		when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("Test Title1");
		when(httpServletRequest.getParameter(Attribute.PUBLISHER)).thenReturn("Test Publisher1");
		when(httpServletRequest.getParameter(Attribute.AVAILABILITY)).thenReturn("subscription");
		when(httpServletRequest.getParameterValues(Attribute.AUTHORS)).thenReturn(authorsIds);

		PostAddBookCommand postAddBookCommand = new PostAddBookCommand(bookService);

		String expectedResultedResource = RedirectionManager.REDIRECTION;
		String actualResultedResource = postAddBookCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(httpServletRequest).getParameter(Attribute.ISBN);
		verify(httpServletRequest).getParameter(Attribute.TITLE);
		verify(httpServletRequest).getParameter(Attribute.PUBLISHER);
		verify(httpServletRequest).getParameter(Attribute.AVAILABILITY);
		verify(httpServletRequest).getParameterValues(Attribute.AUTHORS);
		// verify(bookService).createBook(book);

	}

	@Test
	// @Ignore
	public void testPostAddBookInvalidInputCommand() throws ServletException, IOException {

		/*
		 * HttpServletRequest httpServletRequest =
		 * mock(HttpServletRequest.class); HttpServletResponse
		 * httpServletResponse = mock(HttpServletResponse.class); BookService
		 * bookService = mock(BookService.class); AuthorService authorService =
		 * mock(AuthorService.class);
		 * 
		 * when(httpServletRequest.getParameter(Attribute.ISBN)).thenReturn("");
		 * when(httpServletRequest.getParameter(Attribute.TITLE)).thenReturn("")
		 * ;
		 * when(httpServletRequest.getParameter(Attribute.PUBLISHER)).thenReturn
		 * (""); when(httpServletRequest.getParameter(Attribute.AVAILABILITY)).
		 * thenReturn("subscription");
		 * when(httpServletRequest.getParameterValues(Attribute.AUTHORS)).
		 * thenReturn(null);
		 * 
		 * PostAddBookCommand postAddBookCommand = new
		 * PostAddBookCommand(bookService, authorService);
		 * 
		 * String expectedResultedResource = Page.ADD_BOOK_VIEW; String
		 * actualResultedResource =
		 * postAddBookCommand.execute(httpServletRequest, httpServletResponse);
		 * assertEquals(expectedResultedResource, actualResultedResource);
		 * 
		 * verify(httpServletRequest).getParameter(Attribute.ISBN);
		 * verify(httpServletRequest).getParameter(Attribute.TITLE);
		 * verify(httpServletRequest).getParameter(Attribute.PUBLISHER);
		 * verify(httpServletRequest).getParameter(Attribute.AVAILABILITY);
		 * verify(httpServletRequest).getParameterValues(Attribute.AUTHORS);
		 * verify(authorService).getAllAuthors();
		 * verify(httpServletRequest).setAttribute(eq(Attribute.ERRORS),
		 * anyListOf(String.class));
		 * verify(httpServletRequest).setAttribute(eq(Attribute.BOOK),
		 * anyObject());
		 * verify(httpServletRequest).setAttribute(eq(Attribute.AUTHORS),
		 * anyListOf(Author.class)); verify(bookService,
		 * never()).createBook(anyObject());
		 */
	}
}
