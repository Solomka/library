package ua.training.model.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.model.dao.BookInstancesDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.entity.BookInstance;

public class BookInstancesService {
	private static final Logger LOGGER = LogManager.getLogger(BookInstancesService.class);

	private DaoFactory daoFactory;

	private BookInstancesService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final BookInstancesService INSTANCE = new BookInstancesService(DaoFactory.getDaoFactory());
	}

	public static BookInstancesService getInstance() {
		return Holder.INSTANCE;
	}

	public List<BookInstance> getBookInstances(Long bookId) {
		LOGGER.info("Get book instances: " + bookId);
		try (BookInstancesDao bookInstancesDao = daoFactory.createBookInstancesDao()) {
			return bookInstancesDao.getBookInstances(bookId);
		}
	}

}
