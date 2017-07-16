package ua.training.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ua.training.dao.BookInstanceDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.BookInstance;
import ua.training.testData.BookInstanceTestData;

public class BookInstanceServiceTest {

	private DaoFactory daoFactory;
	private BookInstanceDao bookInstanceDao;
	private BookInstanceService bookInstanceService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		bookInstanceDao = mock(BookInstanceDao.class);
	}

	private void initBookInstanceService() {
		bookInstanceService = new BookInstanceService(daoFactory);
	}

	private void initObjectsMethodsStubbing() {
		when(daoFactory.createBookInstancesDao()).thenReturn(bookInstanceDao);
	}

	@Test
	// @Ignore
	public void testCreateBookInstance() {
		BookInstance bookInstance = BookInstanceTestData.generateBookInstance();

		initObjectsMocking();
		initBookInstanceService();
		initObjectsMethodsStubbing();

		bookInstanceService.createBookInstance(bookInstance);

		verify(daoFactory).createBookInstancesDao();
		verify(bookInstanceDao).create(bookInstance);
	}

	@Test
	// @Ignore
	public void testUpdateBookInstance() {
		BookInstance bookInstance = BookInstanceTestData.generateBookInstance();

		initObjectsMocking();
		initBookInstanceService();
		initObjectsMethodsStubbing();

		bookInstanceService.updateBookInstance(bookInstance);

		verify(daoFactory).createBookInstancesDao();
		verify(bookInstanceDao).update(bookInstance);
	}
}
