package ua.training.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {

	public static final String DB_FILE = "/db.properties";
	private static final String DB_FACTORY_CLASS = "factory.class";

	private static DaoFactory instance;

	public abstract DaoConnection getConnection();

	// class level dao - can call methods from one dao class
	public abstract BookDao createBookDao();

	// business level dao - can call methods from many dao classes
	public abstract BookDao createBookDao(DaoConnection connection);

	/** return JdbcDaoFactory/ JdbcTemplateFactory/ HibernateFactory */
	public static DaoFactory getInstance() {
		if (instance == null) {
			try {
				InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
				Properties dbProps = new Properties();
				dbProps.load(inputStream);
				String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
				instance = (DaoFactory) Class.forName(factoryClass).newInstance();

			} catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}
}
