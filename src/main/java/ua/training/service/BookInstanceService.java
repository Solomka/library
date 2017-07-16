package ua.training.service;

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

	public void createBookInstance(BookInstance bookInstance) {
		LOGGER.info("Create book instance: " + bookInstance.getInventoryNumber());
		try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstancesDao()) {
			bookInstanceDao.create(bookInstance);
		}
	}

	public void updateBookInstance(BookInstance bookInstance) {
		LOGGER.info("Update book instance: " + bookInstance.getId());
		try (BookInstanceDao bookInstancesDao = daoFactory.createBookInstancesDao()) {
			bookInstancesDao.update(bookInstance);
		}
	}
}