package ua.training.model.dao;

import java.util.List;
import java.util.Optional;

import ua.training.model.entity.Librarian;
import ua.training.model.entity.Reader;
import ua.training.model.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable {

	<T extends User> Optional<T> getUserById(Long id);

	<T extends User> Optional<T> getUserByEmail(String email);

	List<Reader> getAllReaders();

	Optional<Reader> searchByReaderCardNumber(String readerCardNumber);

	void createReader(Reader reader);

	void createLibrarian(Librarian librarian);

	void close();
}
