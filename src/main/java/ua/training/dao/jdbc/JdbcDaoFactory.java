package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ua.training.dao.BookDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;

public class JdbcDaoFactory extends DaoFactory {

	private DataSource dataSource;

	/** load database connection pool (using JNDI) */
	public JdbcDaoFactory() {
		try {

			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/library");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** create custom JdbcDAOConnection */
	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookDao createBookDao() {
		try {
			return new JdbcBookDaoImpl(dataSource.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookDao createBookDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookDaoImpl(sqlConnection);
	}

}
