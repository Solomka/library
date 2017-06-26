package ua.training.model.service;

import ua.training.model.dao.DaoFactory;

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
