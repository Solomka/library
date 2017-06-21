package ua.training.service.impl;

import java.util.List;
import java.util.Optional;

import ua.training.dao.BookDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Book;

public class BookService {

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
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getAll();
		}
	}

	public Optional<Book> getBookById(Long id) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.getById(id);
		}
	}

	public void createBook(Book book) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.create(book);
		}

	}

	public void updateBook(Book book) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.update(book);
		}

	}

	public void deleteBook(Long id) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			bookDao.delete(id);
		}

	}

	public List<Book> searchBookByTitle(String title) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchByTitle(title);
		}
	}

	public List<Book> searchBookByAuthorSurname(String authorSurname) {
		try (BookDao bookDao = daoFactory.createBookDao()) {
			return bookDao.searchByAuthorSurname(authorSurname);
		}
	}

}
