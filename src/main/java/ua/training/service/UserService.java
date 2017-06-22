package ua.training.service;

import java.util.Optional;

import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.entity.Reader;

public class UserService {

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

	/*
	public void userReturnBook(String login, Long bookId) {
		Optional<Reader> reader = userDao.getUserByLogin(login);
		// business logic
	} */

}
