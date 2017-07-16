package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import ua.training.dao.AuthorDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Author;
import ua.training.testData.AuthorTestData;

public class AuthorServiceTest {

	private DaoFactory daoFactory;
	private AuthorDao authorDao;
	private AuthorService authorService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		authorDao = mock(AuthorDao.class);
	}

	private void initAuthorService() {
		authorService = new AuthorService(daoFactory);
	}

	private void initObjectsMethodsStubbing() {
		when(daoFactory.createAuthorDao()).thenReturn(authorDao);
	}

	@Test
	// @Ignore
	public void testCreateAuthor() {
		Author author = AuthorTestData.generateAuthor();

		initObjectsMocking();
		initAuthorService();
		initObjectsMethodsStubbing();

		authorService.createAuthor(author);

		verify(daoFactory).createAuthorDao();
		verify(authorDao).create(author);
	}

	@Test
	// @Ignore
	public void testGetAllAuthors() {
		List<Author> authors = AuthorTestData.generateAuthorsList();

		initObjectsMocking();
		initAuthorService();
		initObjectsMethodsStubbing();
		when(authorDao.getAll()).thenReturn(authors);

		List<Author> actualAuthors = authorService.getAllAuthors();

		assertEquals(authors, actualAuthors);
		verify(daoFactory).createAuthorDao();
		verify(authorDao).getAll();
	}
}
