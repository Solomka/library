package ua.training;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.training.command.AllBooksCommandTest;
import ua.training.command.BookInstancesCommandTest;
import ua.training.command.GetAddBookCommandTest;
import ua.training.command.PostAddBookCommandTest;
import ua.training.command.SearchBookByAuthorCommandTest;
import ua.training.command.SearchBookByTitleCommandTest;
import ua.training.regex.RegexTest;
import ua.training.service.BookServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ RegexTest.class, BookServiceTest.class, AllBooksCommandTest.class, BookInstancesCommandTest.class,
		GetAddBookCommandTest.class, PostAddBookCommandTest.class, SearchBookByAuthorCommandTest.class,
		SearchBookByTitleCommandTest.class })
public class TestSuite {

	/*
	 * empty class
	 */
}
