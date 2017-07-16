package ua.training.controller.command.bookInstance;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import ua.training.constants.Attribute;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.BookInstance;
import ua.training.service.BookInstanceService;
import ua.training.testData.BookInstanceTestData;

public class PostAddBookInstanceCommandTest {

	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private BookInstanceService bookInstanceService;
	private PostAddBookInstanceCommand postAddBookInstanceCommand;

	private void initObjectsMocking() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
		bookInstanceService = mock(BookInstanceService.class);
	}

	private void initPostAddBookInstanceCommand() {
		postAddBookInstanceCommand = new PostAddBookInstanceCommand(bookInstanceService);
	}

	private void initObjectsMethodsStubbing() {
		when(httpServletRequest.getContextPath()).thenReturn("\\library");
		when(httpServletRequest.getServletPath()).thenReturn("\\controller");
		when(httpServletRequest.getParameter(Attribute.ID_BOOK)).thenReturn("1");
	}

	private void initObjectsMethodsStubbingForValidInput() {
		when(httpServletRequest.getParameter(Attribute.INVENTORY_NUMBER)).thenReturn("7689587645876");
	}

	private void initObjectsMethodsStubbingForInvalidInput() {
		when(httpServletRequest.getParameter(Attribute.INVENTORY_NUMBER)).thenReturn("");
	}

	@Test
	// @Ignore
	public void testPostAddBookInstanceValidInput() throws ServletException, IOException {
		BookInstance bookInstance = BookInstanceTestData.generateBookInstance();

		initObjectsMocking();
		initPostAddBookInstanceCommand();
		initObjectsMethodsStubbing();
		initObjectsMethodsStubbingForValidInput();

		String expectedResult = RedirectionManager.REDIRECTION;
		String actualResult = postAddBookInstanceCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResult, actualResult);
		verify(bookInstanceService).createBookInstance(bookInstance);
	}

	@Test
	// @Ignore
	public void testPostAddBookInstanceInvalidInput() throws ServletException, IOException {
		BookInstance bookInstance = BookInstanceTestData.generateBookInstance();

		initObjectsMocking();
		initPostAddBookInstanceCommand();
		initObjectsMethodsStubbing();
		initObjectsMethodsStubbingForInvalidInput();

		String expectedResult = RedirectionManager.REDIRECTION;
		String actualResult = postAddBookInstanceCommand.execute(httpServletRequest, httpServletResponse);

		assertEquals(expectedResult, actualResult);
		verify(bookInstanceService, never()).createBookInstance(bookInstance);
	}
}
