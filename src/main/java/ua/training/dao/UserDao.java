package ua.training.dao;

import java.util.Optional;

import ua.training.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable{
	
	public<T extends User> Optional<T> getUserByLogin(String login); 

	void close();
}
