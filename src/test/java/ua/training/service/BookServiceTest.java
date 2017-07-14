package ua.training.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;

import ua.training.dao.AuthorDao;
import ua.training.dao.BookDao;
import ua.training.dao.BookInstanceDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.entity.Author;
import ua.training.entity.Availability;
import ua.training.entity.Book;
import ua.training.service.BookService;

public class BookServiceTest {

	@Test
	//@Ignore
	public void testGetAllBooks() {
		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoConnection daoConnection = mock(DaoConnection.class);
		BookDao bookDao = mock(BookDao.class);
		

		List<Book> books = Arrays.asList(new Book[] {
				new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("2222222222222").setTitle("Test Title2").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("3333333333333").setTitle("Test Title3").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build() });

		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
		when(bookDao.getAll()).thenReturn(books);

		BookService bookService = new BookService(daoFactory);

		List<Book> actualBooks = bookService.getAllBooksWithAuthors();
		assertEquals(books, actualBooks);
		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoFactory).createAuthorDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).getAll();
		verify(daoConnection).commit();
	}

	@Test
	@Ignore
	public void testGetBookById() {
		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoConnection daoConnection = mock(DaoConnection.class);
		BookDao bookDao = mock(BookDao.class);
		AuthorDao authorDao = mock(AuthorDao.class);
		BookInstanceDao bookInstancesDao = mock(BookInstanceDao.class);

		Optional<Book> book = Optional.of(new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1")
				.setPublisher("Test Publisher1").setAvailability(Availability.SUBSCRIPTION).build());

		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
		when(daoFactory.createAuthorDao(daoConnection)).thenReturn(authorDao);
		when(daoFactory.createBookInstancesDao(daoConnection)).thenReturn(bookInstancesDao);
		when(bookDao.getById(anyLong())).thenReturn(book);

		BookService bookService = new BookService(daoFactory);
		//TODO:?
		//Optional<Book> actualBook = bookService.getBookById(anyLong());
		Optional<Book> actualBook = bookService.getBookWithAuthorsAndInstancesById(new Long("2"));
		assertEquals(book.get(), actualBook.get());
		
		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoFactory).createAuthorDao(daoConnection);
		verify(daoFactory).createBookInstancesDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).getById(anyLong());
		//verify(authorDao).getBookAuthors(anyLong());
		//verify(bookInstancesDao).getBookInstances(anyLong());
		verify(daoConnection).commit();
	}

	@Test
	//@Ignore
	public void testCreateBook() {

		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoConnection daoConnection = mock(DaoConnection.class);
		BookDao bookDao = mock(BookDao.class);

		Book book = new Book.Builder().setIsbn("1111111111111").setTitle("Test Title1").setPublisher("Test Publisher1")
				.setAvailability(Availability.SUBSCRIPTION).build();

		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);

		BookService bookService = new BookService(daoFactory);
		//bookService.createBook(book);

		verify(daoFactory).getConnection();		
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).create(book);
		verify(bookDao).saveBookAuthors(book);
		verify(daoConnection).commit();
	}
	
	@Test
	//@Ignore
	public void testSearchBookByTitle() {

		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoConnection daoConnection = mock(DaoConnection.class);
		BookDao bookDao = mock(BookDao.class);
		AuthorDao authorDao = mock(AuthorDao.class);
		
		List<Book> books = Arrays.asList(new Book[] {
				new Book.Builder().setIsbn("1111111111111").setTitle("Test Title").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("2222222222222").setTitle("Test Title").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("3333333333333").setTitle("Test Title").setPublisher("Test Publisher")
						.setAvailability(Availability.SUBSCRIPTION).build() });

		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
		when(daoFactory.createAuthorDao(daoConnection)).thenReturn(authorDao);
		when(bookDao.searchBookWithAuthorsByTitle(anyString())).thenReturn(books);

		BookService bookService = new BookService(daoFactory);

		//List<Book> actualBooks = bookService.searchBookByTitle(anyString());
		List<Book> actualBooks = bookService.searchBookWithAuthorsByTitle("Test Title");
		assertEquals(books, actualBooks);
		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoFactory).createAuthorDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).searchBookWithAuthorsByTitle(anyString());
		//verify(authorDao, times(3)).getBookAuthors(anyLong());
		verify(daoConnection).commit();
	}
	
	@Test
//	@Ignore
	public void testSearchBookByAuthor() {

		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoConnection daoConnection = mock(DaoConnection.class);
		BookDao bookDao = mock(BookDao.class);
		AuthorDao authorDao = mock(AuthorDao.class);
		
		List<Book> books = Arrays.asList(new Book[] {
				new Book.Builder().setIsbn("1111111111111").setTitle("Test Title").setPublisher("Test Publisher1")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("2222222222222").setTitle("Test Title").setPublisher("Test Publisher2")
						.setAvailability(Availability.SUBSCRIPTION).build(),
				new Book.Builder().setIsbn("3333333333333").setTitle("Test Title").setPublisher("Test Publisher3")
						.setAvailability(Availability.SUBSCRIPTION).build() });

		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createBookDao(daoConnection)).thenReturn(bookDao);
		when(daoFactory.createAuthorDao(daoConnection)).thenReturn(authorDao);
		when(bookDao.searchBookWithAuthorsByAuthor(anyString())).thenReturn(books);

		BookService bookService = new BookService(daoFactory);

		//List<Book> actualBooks = bookService.searchBookByAuthor(anyString());
		List<Book> actualBooks = bookService.searchBookWithAuthorsByAuthor("NameTest SurnameTest");
		assertEquals(books, actualBooks);
		verify(daoFactory).getConnection();
		verify(daoFactory).createBookDao(daoConnection);
		verify(daoFactory).createAuthorDao(daoConnection);
		verify(daoConnection).begin();
		verify(bookDao).searchBookWithAuthorsByAuthor(anyString());
		//verify(authorDao, times(3)).getBookAuthors(anyLong());
		verify(daoConnection).commit();
	}

}
