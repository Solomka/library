package ua.training.dao;

import java.util.List;
import java.util.Optional;

import ua.training.entity.Librarian;
import ua.training.entity.Reader;
import ua.training.entity.Role;
import ua.training.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable {
	
	<T extends User> List<T> getAllUsers(Role role);

	<T extends User> Optional<T> getUserById(Long id);

	<T extends User> Optional<T> getUserByEmail(String email);

	void createReader(Reader reader);

	void createLibrarian(Librarian librarian);

	void close();

	
}
