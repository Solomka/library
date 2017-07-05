package ua.training.model.dao;

import java.util.List;

import ua.training.model.entity.BookInstance;

public interface BookInstancesDao extends GenericDao<BookInstance, Long>, AutoCloseable {

	List<BookInstance> getBookInstances(Long bookId);
	
	void close();
}
