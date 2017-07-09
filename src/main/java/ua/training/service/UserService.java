package ua.training.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.controller.dto.CredentialsDto;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.entity.Reader;
import ua.training.entity.User;
import ua.training.hashing.PasswordHashing;
import ua.training.locale.MessageUtils;

public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	private static final PasswordHashing passwordHashing = PasswordHashing.getInstance();
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

	public List<Reader> getAllReaders() {
		LOGGER.info("Get all readers");
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getAllReaders();
		}
	}

	public Optional<User> getUserByEmail(CredentialsDto credentials) {
		LOGGER.info("Get user by emil: " + credentials.getEmail() + MessageUtils.COMMA + credentials.getPassword());
		try (UserDao userDao = daoFactory.createUserDao()) {
			Optional<User> user = userDao.getUserByEmail(credentials.getEmail());
			if (user.isPresent() && isPasswordValid(credentials.getPassword(), user.get())) {
				return user;
			}
			return Optional.empty();
		}
	}

	public void createReader(Reader reader) {
		LOGGER.info("Create reader: " + reader);
		hashUserPassword(reader);
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			UserDao userDao = daoFactory.createUserDao(connection);
			userDao.create(reader);
			userDao.createReader(reader);
			connection.commit();
		}
	}
	
	public boolean changePassword(User user, String oldPassword, String newPassword) {
		LOGGER.info("Change user password");
		if (isPasswordValid(oldPassword, user)) {
			user.setPassword(newPassword);
			hashUserPassword(user);
			try (UserDao userDao = daoFactory.createUserDao()) {
				userDao.update(user);
				return true;
			}
		}
		return false;
	}

	/*public boolean changePassword(User user, String oldPassword, String newPassword) {
		LOGGER.info("Change user password");
		if (isPasswordValid(oldPassword, user)) {
			user.setPassword(newPassword);
			hashUserPassword(user);
			try (DaoConnection connection = daoFactory.getConnection()) {
				connection.begin();
				UserDao userDao = daoFactory.createUserDao(connection);
				Optional<Reader> reader = userDao.getUserById(user.getId());
				reader.get().setPassword(user.getPassword());
				reader.get().setSalt(user.getSalt());
				userDao.update(user);
				connection.commit();
				return true;
			}
		}
		return false;
	}
	*/
	public Optional<Reader> getReaderByReaderCardNumber(String readerCardNumber){
		try(UserDao userDao = daoFactory.createUserDao()){
			return userDao.searchByReaderCardNumber(readerCardNumber);
		}
	}

	private void hashUserPassword(User user) {
		byte[] salt = passwordHashing.generateRandomSalt();
		String password = passwordHashing.generatePassHash256(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(password);
	}

	private boolean isPasswordValid(String password, User user) {
		return passwordHashing.checkPassword(password, user.getSalt(), user.getPassword());
	}

}
