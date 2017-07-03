package ua.training.model.dao;

import java.util.List;

import ua.training.model.entity.Author;

public interface AuthorDao extends GenericDao<Author, Long>, AutoCloseable {

	List<Author> getBookAuthors(Long bookId);
	
	void close();

}
