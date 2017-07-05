package ua.training.controller.constants;

public final class Page {

	
	public static String PREFIX = "/WEB-INF/views/";
	public static String SUFFIX = ".jsp";

	private Page() {
	}

	public static String HOME_VIEW = "/index" + SUFFIX;	
	public static String LOGIN_VIEW = PREFIX + "login" + SUFFIX;
	
	public static String ALL_BOOKS_VIEW = PREFIX + "allBooks" + SUFFIX;
	public static final String BOOK_INSTANCES_VIEW = PREFIX + "bookInstances" + SUFFIX;
	public static String ADD_BOOK_VIEW = PREFIX + "addBook" + SUFFIX;
}
