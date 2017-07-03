package ua.training.model.dao;

import java.util.Optional;

import ua.training.model.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable{
	
	public<T extends User> Optional<T> getUserByEmail(String email); 

	//public<T extends User> Optional<T> getUserByLoginTest(String email);
	
	void close();
}
