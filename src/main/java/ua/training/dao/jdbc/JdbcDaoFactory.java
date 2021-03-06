package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.AuthorDao;
import ua.training.dao.BookDao;
import ua.training.dao.BookInstanceDao;
import ua.training.dao.BookOrderDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.exception.ServerException;

/**
 * Class that represent dao factory that produces DAOs for JDBC persistent
 * storage access implementation and use DB connection pool for getting
 * connections to db
 * 
 * @author Solomka
 *
 */
public class JdbcDaoFactory extends DaoFactory {

	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);

	private DataSource dataSource;

	/**
	 * Get DataSource implementation from Initial Context by means of JNDI mechanism
	 */
	public JdbcDaoFactory() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/library");

		} catch (Exception e) {
			LOGGER.error("Can't load pool connection from Initial Context", e);
			throw new ServerException(e);
		}
	}

	/**
	 * Get custom Connection wrapper for providing transaction execution
	 * 
	 * @return a connection to the data source
	 * @exception ServerException
	 *                if a database access error occurs
	 */
	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection to the data source", e);
			throw new ServerException(e);
		}
	}

	@Override
	public UserDao createUserDao() {
		try {
			return new JdbcUserDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcUserDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public UserDao createUserDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcUserDao(sqlConnection);
	}

	@Override
	public BookDao createBookDao() {
		try {
			return new JdbcBookDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcBookDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public BookDao createBookDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookDao(sqlConnection);
	}

	@Override
	public AuthorDao createAuthorDao() {
		try {
			return new JdbcAuthorDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcAuthorDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public AuthorDao createAuthorDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcAuthorDao(sqlConnection);
	}

	@Override
	public BookInstanceDao createBookInstancesDao() {
		try {
			return new JdbcBookInstanceDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcBookInstancesDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public BookInstanceDao createBookInstancesDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookInstanceDao(sqlConnection);
	}

	@Override
	public BookOrderDao createBookOrderDao() {
		try {
			return new JdbcBookOrderDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcBookOrderDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public BookOrderDao createBookOrderDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookOrderDao(sqlConnection);
	}
}
