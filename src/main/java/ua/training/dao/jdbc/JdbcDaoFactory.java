package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.model.dao.CityDao;
import ua.training.model.dao.PersonDao;
import ua.training.model.dao.TeamDao;

public class JdbcDaoFactory extends DaoFactory {

	private static final String DB_URL = "url";

	private DataSource dataSource;

	/** load database connection pool (using JNDI) */
	public JdbcDaoFactory() {
		try {

			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/football");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CityDao createCityDao() {
		try {
			return new JdbcCityDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PersonDao createPersonDao() {
		return null;// new JdbcPersonDao(connection);
	}

	@Override
	public TeamDao createTeamDao() {
		return null;// return new JdbcTeamDao();
	}

	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CityDao createCityDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcCityDao(sqlConnection);

	}

	@Override
	public PersonDao createPersonDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcPersonDao(sqlConnection);
	}

	@Override
	public TeamDao createTeamDao(DaoConnection connection) {
		// TODO Auto-generated method stub
		return null;
	}

}
