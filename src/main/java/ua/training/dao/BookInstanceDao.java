package ua.training.dao;

import ua.training.entity.BookInstance;

public interface BookInstanceDao extends GenericDao<BookInstance, Long>, AutoCloseable {

	void close();
}