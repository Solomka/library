package ua.training.dao;

import java.util.List;

import ua.training.entity.Author;

public interface AuthorDao extends GenericDao<Author, Long>, AutoCloseable {

	List<Author> getBookAuthors(Long bookId);
	
	void close();

}
