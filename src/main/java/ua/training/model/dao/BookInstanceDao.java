package ua.training.model.dao;

import java.util.List;

import ua.training.model.entity.BookInstance;

public interface BookInstanceDao extends GenericDao<BookInstance, Long>, AutoCloseable {

	List<BookInstance> getBookInstances(Long bookId);
	void addBookInstance(BookInstance bookInstance);
	
	void close();
}
