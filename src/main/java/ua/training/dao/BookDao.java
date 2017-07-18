package ua.training.dao;

import java.util.List;
import java.util.Optional;

import ua.training.entity.Book;

public interface BookDao extends GenericDao<Book, Long>, AutoCloseable {

	List<Book> searchBookWithAuthorsByTitle(String title);

	List<Book> searchBookWithAuthorsByAuthor(String authorSurname);

	void saveBookAuthors(Book book);

	Optional<Book> getBookWithAvailableInstances(Long id);
	
	Optional<Book> searchBookWithAuthorsByInstanceId(Long instanceId);
	
	List<Book> getAllBooksWithAuthorsPagination(int limit, int offset);
	
	int countAllBooks();

	void close();		
}