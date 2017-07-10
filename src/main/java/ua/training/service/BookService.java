package ua.training.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.AuthorDao;
import ua.training.dao.BookDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.entity.Book;

public class BookService {

	private static final Logger LOGGER = LogManager.getLogger(BookService.class);

	private DaoFactory daoFactory;

	BookService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookService INSTANCE = new BookService(DaoFactory.getDaoFactory());
	}

	public static BookService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Book> getBooksWithAuthors() {
		LOGGER.info("Get all books with authors");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getAll();
		}
	}
	
	public Optional<Book> getBookWithAuthorsAndInstances(Long bookId) {
		LOGGER.info("Get book with authors and instances: " + bookId);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			Optional<Book> book = bookDao.getById(bookId);
			return book;
		}
	}

	public Optional<Book> getBookWithAuthorsAndAvailableInstances(Long bookId) {
		LOGGER.info("Get book with authors and available instances: " + bookId);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			Optional<Book> book = bookDao.getBookWithAvailableInstances(bookId);
			return book;
		}
	}

	public List<Book> searchBookByTitle(String title) {
		LOGGER.info("Search book by title");
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			AuthorDao authorDao = daoFactory.createAuthorDao(connection);
			List<Book> books = bookDao.searchByTitle(title);
			for (Book book : books) {
				book.setAuthors(authorDao.getBookAuthors(book.getId()));
			}
			connection.commit();
			return books;
		}
	}

	public List<Book> searchBookByAuthor(String author) {
		LOGGER.info("Search book by author surname");
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			AuthorDao authorDao = daoFactory.createAuthorDao(connection);
			List<Book> books = bookDao.searchByAuthor(author);
			for (Book book : books) {
				book.setAuthors(authorDao.getBookAuthors(book.getId()));
			}
			connection.commit();
			return books;
		}
	}
	
	public Optional<Book> searchBookByInstanceInventoryNumber(String instanceInventoryNumber){
		LOGGER.info("Search book by instanceInventoryNumber: " + instanceInventoryNumber);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchByBookInstanceInventoryNumber(instanceInventoryNumber);
		}		
	}

	public void createBook(Book book) {
		LOGGER.info("Create book with authors: " + book);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			bookDao.create(book);
			bookDao.saveBookAuthors(book);
			connection.commit();
		}
	}

	public void updateBook(Book book) {
		LOGGER.info("Update book: " + book);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.update(book);
		}

	}

	public void deleteBook(Long id) {
		LOGGER.info("Delete book: " + id);
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.delete(id);
		}
	}
}
