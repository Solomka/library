package ua.training.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.BookDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Book;

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

	public List<Book> getAllBooks() {
		LOGGER.info("Get all books");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getAll();
		}
	}

	public Optional<Book> getBookById(Long id) {
		LOGGER.info("Get book by id");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getById(id);
		}
	}

	public void createBook(Book book) {
		LOGGER.info("Create book");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.create(book);
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
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchByTitle(title);
		}
	}

	public List<Book> searchBookByAuthorSurname(String authorSurname) {
		LOGGER.info("Search book by author surname");
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchByAuthorSurname(authorSurname);
		}
	}

}