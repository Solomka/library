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

	public Optional<User> getUserByEmail(CredentialsDto credentials) {
		LOGGER.info("Get user by emil: " + credentials.getEmail() + ", " + credentials.getPassword());
		try (UserDao userDao = daoFactory.createUserDao()) {
			Optional<User> user = userDao.getUserByEmail(credentials.getEmail());
			if (user.isPresent() && isPasswordValid(credentials.getPassword(), user.get())) {
				return user;
			}
			return Optional.empty();
		}
	}

	private boolean isPasswordValid(String password, User user) {
		return PasswordHashing.getInstance().checkPassword(password, user.getSalt(), user.getPassword());
	}

}
