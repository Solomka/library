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
	private DaoConnection daoConnection;
	private BookDao bookDao;	
	private BookService bookService;

	private void initObjectsMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		bookDao = mock(BookDao.class);		
	}
	
	private void initBookService(){
		bookService = new BookService(daoFactory);		
	}

	private void initBookDaoCreationStubbing() {
		when(daoFactory.createBookDao()).thenReturn(bookDao);
	}

	private void initBookDaoCreationWithDaoConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
	}

	@Test
	// @Ignore
	public void testGetAllBooksWithAuthors() {
		List<Book> books = BookTestData.generateBooksListWithAuthors();

		initObjectsMocking();
		initBookService();
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
		initBookService();
		initBookDaoCreationStubbing();
		when(bookDao.getById(anyLong())).thenReturn(book);

		Long bookId = new Long(1);
		Optional<Book> actualBook = bookService.getBookWithAuthorsAndInstancesById(bookId);

		assertEquals(book.get(), actualBook.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).getById(bookId);
	}

	@Test
	// @Ignore
	public void testGetBookWithAuthorsAndAvailableInstancesById() {
		Optional<Book> book = BookTestData.generateBookOptionalWithAuthorsAndAvailableInstances();

		initObjectsMocking();
		initBookService();
		initBookDaoCreationStubbing();
		when(bookDao.getBookWithAvailableInstances(anyLong())).thenReturn(book);

		Long bookId = new Long(1);
		Optional<Book> actualBook = bookService.getBookWithAuthorsAndAvailableInstancesById(bookId);

		assertEquals(book.get(), actualBook.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).getBookWithAvailableInstances(bookId);
	}

	@Test
	// @Ignore
	public void testSearchBookWithAuthorsByTitle() {
		List<Book> books = BookTestData.generateBooksListWithAuthors();

		initObjectsMocking();
		initBookService();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByTitle(anyString())).thenReturn(books);

		String title = "Test Title";
		List<Book> actualBooks = bookService.searchBookWithAuthorsByTitle(title);

		assertEquals(books, actualBooks);
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByTitle(title);
	}

	@Test
	// @Ignore
	public void testSearchBookWithAuthorsByAuthor() {
		List<Book> books = BookTestData.generateBooksListWithSameAuthor();

		initObjectsMocking();
		initBookService();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);

		String author = "Daniel Keyes";
		List<Book> actualBooks = bookService.searchBookWithAuthorsByAuthor(author);

		assertEquals(books, actualBooks);
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByAuthor(author);
	}

	@Test
	// @Ignore
	public void searchBookWithAuthorsByInstanceId() {
		Optional<Book> book = BookTestData.generateBookOptionalWithAuthors();

		initObjectsMocking();
		initBookService();
		initBookDaoCreationStubbing();
		when(bookDao.searchBookWithAuthorsByInstanceId(anyLong())).thenReturn(book);

		Long instanceId = new Long(1);
		Optional<Book> actualBooks = bookService.searchBookWithAuthorsByInstanceId(instanceId);

		assertEquals(book.get(), actualBooks.get());
		verify(daoFactory).createBookDao();
		verify(bookDao).searchBookWithAuthorsByInstanceId(instanceId);
	}

	@Test
	// @Ignore
	public void testCreateBook() {
		BookDto bookDto = BookTestData.generateBookDtoWithAuthors();
		Book book = BookTestData.generateBookForCreation();

		initObjectsMocking();
		initBookService();
		initBookDaoCreationWithDaoConnectionStubbing();

		bookService.createBook(bookDto);

		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).create(book);
		verify(bookDao).saveBookAuthors(book);
		verify(daoConnection).commit();
	}
}
