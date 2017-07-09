package ua.training.dao;

import java.util.List;

import ua.training.entity.BookInstance;

public interface BookInstanceDao extends GenericDao<BookInstance, Long>, AutoCloseable {

	List<BookInstance> getBookInstances(Long bookId);
	void addBookInstance(BookInstance bookInstance);
	
	void close();
}
