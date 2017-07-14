package ua.training;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.training.command.book.AllBooksCommandTest;
import ua.training.command.book.PostAddBookCommandTest;
import ua.training.command.book.SearchBookByAuthorCommandTest;
import ua.training.command.book.SearchBookByTitleCommandTest;
import ua.training.regex.RegexTest;
import ua.training.service.BookServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ RegexTest.class, BookServiceTest.class, AllBooksCommandTest.class, PostAddBookCommandTest.class,
		SearchBookByAuthorCommandTest.class, SearchBookByTitleCommandTest.class })
public class TestSuite {

	/*
	 * empty class
	 */
}
