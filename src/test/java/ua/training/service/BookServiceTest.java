package ua.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import ua.training.dao.BookDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dto.BookDto;
import ua.training.entity.Book;
import ua.training.testData.BookTestData;

public class BookServiceTest {

	private DaoFactory daoFactory;
	private BookDao bookDao;
	private DaoConnection daoConnection;
	private BookService bookService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		bookDao = mock(BookDao.class);
		daoConnection = mock(DaoConnection.class);
		bookService = new BookService(daoFactory);
	}

	private void initBookDaoCreationStubbing() {
		when(daoFactory.createBookDao()).thenReturn(bookDao);
	}

	private void initBookDaoCreationWithConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
	}

	@Test
	// @Ignore
	public void testGetAllBooksWithAuthors() {
		List<Book> books = BookTestData.generateBooksListWithAuthors();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.getAll()).thenReturn(books);

		List<Book> actualBooks = bookService.getAllBooksWithAuthors();

		assertEquals(books, actualBooks);
		verify(daoFactory).createBookDao();
		verify(bookDao).getAll();
	}

	@Test
	// @Ignore
	public void testGetBookWithAuthorsAndInstancesById() {
		Optional<Book> book = BookTestData.generateBookOptionalWithAuthorsAndInstances();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.getById(anyLong())).thenReturn(book);

		Optional<Book> actualBook = bookService.getBookWithAuthorsAndInstancesById(new Long(1));

		assertEquals(book.get(), actualBook.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).getById(anyLong());
	}

	@Test
	// @Ignore
	public void testGetBookWithAuthorsAndAvailableInstancesById() {
		Optional<Book> book = BookTestData.generateBookOptionalWithAuthorsAndInstances();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.getBookWithAvailableInstances(anyLong())).thenReturn(book);

		Optional<Book> actualBook = bookService.getBookWithAuthorsAndAvailableInstancesById(new Long(1));

		assertEquals(book.get(), actualBook.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).getBookWithAvailableInstances(anyLong());
	}

	@Test
	// @Ignore
	public void testSearchBookWithAuthorsByTitle() {
		List<Book> books = BookTestData.generateBooksListWithAuthors();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByTitle(anyString())).thenReturn(books);

		List<Book> actualBooks = bookService.searchBookWithAuthorsByTitle("Test Title");

		assertEquals(books, actualBooks);
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByTitle(anyString());
	}

	@Test
	// @Ignore
	public void testSearchBookWithAuthorsByAuthor() {
		List<Book> books = BookTestData.generateBooksListWithSameAuthor();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);

		List<Book> actualBooks = bookService.searchBookWithAuthorsByAuthor("Daniel Keyes");

		assertEquals(books, actualBooks);
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByAuthor(anyString());
	}

	@Test
	// @Ignore
	public void searchBookWithAuthorsByInstanceId() {
		Optional<Book> book = BookTestData.generateBookOptionalWithAuthors();

		initObjectsMocking();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByInstanceId(anyLong())).thenReturn(book);

		Optional<Book> actualBooks = bookService.searchBookWithAuthorsByInstanceId(new Long(1));

		assertEquals(book.get(), actualBooks.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByInstanceId(anyLong());
	}

	@Test
	// @Ignore
	public void testCreateBook() {
		BookDto bookDto = BookTestData.generateBookDtoWithAuthors();
		Book book = BookTestData.generateBookForCreation();

		initObjectsMocking();
		initBookDaoCreationWithConnectionStubbing();

		bookService.createBook(bookDto);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).create(book);
		verify(bookDao).saveBookAuthors(book);
		verify(daoConnection).commit();
	}
}
