package ua.training.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	private DaoFactory daoFactory;

	private UserService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final UserService INSTANCE = new UserService(DaoFactory.getDaoFactory());
	}

	public static UserService getInstance() {
		return Holder.INSTANCE;
	}

	public Optional<User> getUserByLogin(String login) {
		LOGGER.info("Get user by login");
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getUserByLogin(login);
		}
	}
	/*
	 * public void userReturnBook(String login, Long bookId) { Optional<Reader>
	 * reader = userDao.getUserByLogin(login); // business logic }
	 */

}
