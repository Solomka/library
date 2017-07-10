package ua.training.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.BookInstanceDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.BookInstance;

public class BookInstanceService {
	private static final Logger LOGGER = LogManager.getLogger(BookInstanceService.class);

	private DaoFactory daoFactory;

	BookInstanceService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookInstanceService INSTANCE = new BookInstanceService(DaoFactory.getDaoFactory());
	}

	public static BookInstanceService getInstance() {
		return Holder.INSTANCE;
	}

	public List<BookInstance> getBookInstances(Long bookId) {
		LOGGER.info("Get book instances: " + bookId);
		try (BookInstanceDao bookInstancesDao = daoFactory.createBookInstancesDao()) {
			return bookInstancesDao.getBookInstances(bookId);
		}
	}

	public void addBookInstance(BookInstance bookInstance) {
		LOGGER.info("Add book instance: " + bookInstance);
		try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao()) {
			bookInstanceDao.addBookInstance(bookInstance);
		}
	}
}
