package ua.training.model.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.model.dao.AuthorDao;
import ua.training.model.dao.BookDao;
import ua.training.model.dao.BookInstancesDao;
import ua.training.model.dao.DaoConnection;
import ua.training.model.dao.DaoFactory;
import ua.training.model.entity.Book;

public class BookService {

	private static final Logger LOGGER = LogManager.getLogger(BookService.class);

	private DaoFactory daoFactory;

	private BookService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookService INSTANCE = new BookService(DaoFactory.getDaoFactory());
	}

	public static BookService getInstance() {
		return Holder.INSTANCE;
	}

	public Optional<Book> getBook(Long bookId) {
		LOGGER.info("Get book with authors and instances: " + bookId);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			AuthorDao authorDao = daoFactory.createAuthorDao(connection);
			BookInstancesDao bookInstancesDao = daoFactory.createBookInstancesDao(connection);

			Optional<Book> book = bookDao.getById(bookId);
			if (book.isPresent()) {
				book.get().setAuthors(authorDao.getBookAuthors(bookId));
				book.get().setBookInstances(bookInstancesDao.getBookInstances(bookId));
			}
			connection.commit();
			return book;
		}
	}

	/*
	 * public List<Book> getAllBooks() { LOGGER.info("Get all books"); try
	 * (BookDao bookDao = daoFactory.createBookDao()) { return bookDao.getAll();
	 * } }
	 */

	public List<Book> getAllBooks() {
		LOGGER.info("Get all books with authors");
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			AuthorDao authorDao = daoFactory.createAuthorDao(connection);
			List<Book> books = bookDao.getAll();
			for (Book book : books) {
				book.setAuthors(authorDao.getBookAuthors(book.getId()));
			}
			connection.commit();
			return books;
		}
	}

	public Optional<Book> getBookById(Long id) {
		LOGGER.info("Get book by id");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getById(id);
		}
	}

	public void createBook(Book book) {
		LOGGER.info("Create book with authors");
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			BookDao bookDao = daoFactory.createBookDao(connection);
			bookDao.create(book);
			bookDao.saveBookAuthors(book);
			connection.commit();
		}

	}

	public void updateBook(Book book) {
		LOGGER.info("Update book");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.update(book);
		}

	}

	public void deleteBook(Long id) {
		LOGGER.info("Delete book");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.delete(id);
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

	public List<Book> searchBookByAuthorSurname(String author) {
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

}
