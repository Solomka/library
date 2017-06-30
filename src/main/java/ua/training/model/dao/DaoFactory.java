package ua.training.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

	public static final String DB_FILE = "/db.properties";
	private static final String DB_FACTORY_CLASS = "factory.class";

	private static DaoFactory daoFactory;

	public abstract DaoConnection getConnection();

	public abstract UserDao createUserDao();

	public abstract UserDao createUserDao(DaoConnection connection);

	// class level dao - can call methods from one dao class
	public abstract BookDao createBookDao();

	// business level dao - can call methods from many dao classes
	public abstract BookDao createBookDao(DaoConnection connection);

	public static DaoFactory getDaoFactory() {
		if (daoFactory == null) {
			try {
				InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
				Properties dbProps = new Properties();
				dbProps.load(inputStream);
				String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
				daoFactory = (DaoFactory) Class.forName(factoryClass).newInstance();

			} catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		return daoFactory;
	}

}