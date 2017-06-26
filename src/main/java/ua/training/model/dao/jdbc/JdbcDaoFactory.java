package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ua.training.model.dao.BookDao;
import ua.training.model.dao.DaoConnection;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;

/**
 * Class represents dao factory that produces many DAOs for a Jdbc database
 * implementation and use database connection pool for getting connections to
 * db
 * 
 * @author Solomka
 *
 */
public class JdbcDaoFactory extends DaoFactory {

	private DataSource dataSource;

	/**
	 * Get data source by means of JNDI mechanism
	 */
	public JdbcDaoFactory() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/library");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get custom Connection wrapper for correct transaction execution
	 * 
	 * @return a connection to the data source
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDao createUserDao() {
		try {
			return new JdbcUserDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
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
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookDao createBookDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookDao(sqlConnection);
	}

}
