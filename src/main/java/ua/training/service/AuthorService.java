package ua.training.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.AuthorDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Author;

public class AuthorService {

	private static final Logger LOGGER = LogManager.getLogger(AuthorService.class);

	private DaoFactory daoFactory;

	AuthorService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final AuthorService INSTANCE = new AuthorService(DaoFactory.getDaoFactory());
	}

	public static AuthorService getInstance() {
		return Holder.INSTANCE;
	}

	public void createAuthor(Author author) {
		LOGGER.info("Create author: " + author);
		try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
			authorDao.create(author);
		}
	}

	public List<Author> getAllAuthors() {
		LOGGER.info("Get all authors");
		try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
			return authorDao.getAll();
		}
	}

}
