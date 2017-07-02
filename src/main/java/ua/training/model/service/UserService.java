package ua.training.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.controller.dto.CredentialsDto;
import ua.training.hashing.PasswordHashing;
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

	public boolean isUserWithCredentials(CredentialsDto credentials) {
		LOGGER.info(
				"Check if user with credantials exists: " + credentials.getEmail() + ", " + credentials.getPassword());
		try (UserDao userDao = daoFactory.createUserDao()) {
			Optional<User> user = userDao.getUserByLoginTest(credentials.getEmail());
			if (user.isPresent()) {
				System.out.println("User present: " + credentials.getEmail());
				return PasswordHashing.checkPassword(credentials.getPassword(),
						new byte[] { 112, 40, 104, 6, -38, 34, -127, 50, -46, 76, -89, 22, -123, -107, 8, -70 },
						user.get().getPassword());
			}
			return false;
		}
	}
	
	public Optional<User> getUserByLogin(String login) {
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getUserByLoginTest(login);
		}

	}

	
	/*
	 * public void userReturnBook(String login, Long bookId) { Optional<Reader>
	 * reader = userDao.getUserByLogin(login); // business logic }
	 */

}
