package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import ua.training.dao.DaoConnection;

public class JdbcDaoConnection implements DaoConnection {

	private Connection connection;
	private boolean inTransaction = false;

	JdbcDaoConnection(Connection connection) {
		super();
		this.connection = connection;
	}

	Connection getConnection() {
		return connection;
	}

	@Override
	public void begin() {
		try {
			connection.setAutoCommit(false);
			inTransaction = true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void commit() {
		try {
			connection.commit();
			inTransaction = false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			connection.rollback();
			inTransaction = false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void close() {
		if (inTransaction) {
			rollback();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
