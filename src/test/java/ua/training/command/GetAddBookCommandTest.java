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

import org.junit.Test;

import ua.training.constants.Page;
import ua.training.controller.command.GetAddBookCommand;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.service.AuthorService;

public class GetAddBookCommandTest {

	@Test
	// @Ignore
	public void testGetAddBookCommand() throws ServletException, IOException {

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		AuthorService authorService = mock(AuthorService.class);

		List<Author> authors = Arrays.asList(new Author[] {
				new Author.Builder().setId(new Long("1")).setName("Name").setSurname("Surname").setCountry("Country")
						.build(),
				new Author.Builder().setId(new Long("2")).setName("NameTest").setSurname("SurnameTest")
						.setCountry("CountryTest").build(),
				new Author.Builder().setId(new Long("3")).setName("NameBla").setSurname("SurnameBla")
						.setCountry("CountryBla").build() });

		when(authorService.getAllAuthors()).thenReturn(authors);

		GetAddBookCommand getAddBookCommand = new GetAddBookCommand(authorService);

		String expectedResultedResource = Page.ADD_BOOK_VIEW;
		String actualResultedResource = getAddBookCommand.execute(httpServletRequest, httpServletResponse);
		assertEquals(expectedResultedResource, actualResultedResource);

		verify(authorService).getAllAuthors();
		verify(httpServletRequest).setAttribute(anyString(), eq(authors));
		verify(httpServletRequest).setAttribute(anyString(), eq(Availability.getValues()));
	}

}
