package ua.training.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.converter.BookDtoBookConverter;
import ua.training.dao.BookDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dto.BookDto;
import ua.training.entity.Book;

public class BookService {

	private static final Logger LOGGER = LogManager.getLogger(BookService.class);

	private DaoFactory daoFactory;

	public BookService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookService INSTANCE = new BookService(DaoFactory.getDaoFactory());
	}

	public static BookService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Book> getAllBooksWithAuthors() {
		LOGGER.info("Get all books with authors");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getAll();
		}
	}

	public Optional<Book> getBookWithAuthorsAndInstancesById(Long bookId) {
		LOGGER.info("Get book with authors and instances by id: " + bookId);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			Optional<Book> book = bookDao.getById(bookId);
			return book;
		}
	}

	public Optional<Book> getBookWithAuthorsAndAvailableInstancesById(Long bookId) {
		LOGGER.info("Get book with authors and available instances by id: " + bookId);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			Optional<Book> book = bookDao.getBookWithAvailableInstances(bookId);
			return book;
		}
	}

	public List<Book> searchBookWithAuthorsByTitle(String title) {
		LOGGER.info("Search book with authors by title: " + title);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchBookWithAuthorsByTitle(title);
		}
	}

	public List<Book> searchBookWithAuthorsByAuthor(String author) {
		LOGGER.info("Search book with authors by author: " + author);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchBookWithAuthorsByAuthor(author);
		}
	}

	public Optional<Book> searchBookWithAuthorsByInstanceId(Long instanceId) {
		LOGGER.info("Search book with authors by instance id: " + instanceId);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchBookWithAuthorsByInstanceId(instanceId);
		}
	}

	public void createBook(BookDto bookDto) {
		LOGGER.info("Create book with authors: " + bookDto.getIsbn());
		Book book = BookDtoBookConverter.toBook(bookDto);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			bookDao.create(book);
			bookDao.saveBookAuthors(book);
			connection.commit();
		}
	}
}
