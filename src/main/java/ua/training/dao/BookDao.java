package ua.training.dao;

import java.util.List;
import java.util.Optional;

import ua.training.entity.Book;

public interface BookDao extends GenericDao<Book, Long>, AutoCloseable {

	List<Book> searchByTitle(String title);

	List<Book> searchByAuthor(String authorSurname);

	void close();

	void saveBookAuthors(Book book);
	
	Optional<Book> getBookWithAvailableInstances(Long id);
}
