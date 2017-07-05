package ua.training.model.dao;

import java.util.Optional;

import ua.training.model.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable{
	
	public <T extends User> Optional<T> getUserById(Long id);
	public<T extends User> Optional<T> getUserByEmail(String email); 

	void close();
}
