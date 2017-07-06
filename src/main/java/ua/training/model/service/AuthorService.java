package ua.training.model.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.model.dao.AuthorDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.entity.Author;

public class AuthorService {

	private static final Logger LOGGER = LogManager.getLogger(AuthorService.class);

	private DaoFactory daoFactory;

	private AuthorService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final AuthorService INSTANCE = new AuthorService(DaoFactory.getDaoFactory());
	}

	public static AuthorService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Author> getAllAuthors() {
		LOGGER.info("Get all authors");
		try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
			return authorDao.getAll();
		}
	}

	public List<Author> getBookAuthors(Long bookId) {
		LOGGER.info("Get book authors: " + bookId);
		try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
			return authorDao.getBookAuthors(bookId);
		}
	}

}
