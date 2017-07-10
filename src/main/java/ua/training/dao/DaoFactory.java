package ua.training.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.exception.ServerException;

/**
 * Abstract class that represents dao factory that produces many DAOs for a
 * single database implementation
 * <p>
 * Abstract class instance implementation is loaded from db.properties
 * 
 * @author Solomka
 *
 */
public abstract class DaoFactory {

	private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);

	public static final String DB_FILE = "/db.properties";
	private static final String DB_FACTORY_CLASS = "factory.class";

	private static DaoFactory daoFactory;

	public abstract DaoConnection getConnection();

	public abstract UserDao createUserDao();

	public abstract UserDao createUserDao(DaoConnection connection);

	// class level dao
	public abstract BookDao createBookDao();

	// business level dao
	public abstract BookDao createBookDao(DaoConnection connection);

	public abstract BookInstanceDao createBookInstancesDao();

	public abstract BookInstanceDao createBookInstancesDao(DaoConnection connection);

	public abstract AuthorDao createAuthorDao();

	public abstract AuthorDao createAuthorDao(DaoConnection connection);

	public static DaoFactory getDaoFactory() {
		if (daoFactory == null) {
			try {
				InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
				Properties dbProps = new Properties();
				dbProps.load(inputStream);
				String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
				daoFactory = (DaoFactory) Class.forName(factoryClass).newInstance();

			} catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
				LOGGER.error("Can't load inputStream db config file to properties object", e);
				throw new ServerException(e);
			}
		}
		return daoFactory;
	}
}
