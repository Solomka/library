package ua.training.locale;

public final class Message {

	private Message() {

	}

	public static String PAGE_NOT_FOUND_ERROR = "library.error.pageNotFoundError";
	public static String SERVER_ERROR = "library.error.serverError";
	public static String DIRECT_VIEW_ACCESS_ERROR = "library.error.directViewAccessError";
	public static String UNAUTHORIZED_ACCESS_ERROR = "library.error.authorizedAccessError";

	public static String INVALID_TEXT_INPUT = "library.error.invalidTextInput";

	public static String INVALID_EMAIL = "library.error.invalidEmail";
	public static String INVALID_PASS = "library.error.invalidPass";
	public static String INVALID_CREDENTIALS = "library.error.invalidCredentials";

	public static String INVALID_ISBN = "library.error.invalidISBN";
	public static String INVALID_TITLE = "library.error.invalidTitle";

	public static String INVALID_INVENTORY_NUMBER = "library.error.invalidIInventoryNumber";

	public static String INVALID_PHONE = "library.error.invalidPhone";
	public static String INVALID_ADDRESS = "library.error.invalidAddress";
	public static String INVALID_READER_CARD_NUMBER = "library.error.invalidReaderCardNumber";
	
	public static String AVAILABILITY_READING_ROOM = "library.book.availability.readingRoom";
	public static String AVAILABILITY_SUBSCRIPTION = "library.book.availability.subscription";

	
	public static String STATUS_AVAILABLE = "library.bookInstance.status.available";
	public static String STATUS_UNAVAILABLE = "library.bookInstance.status.unavailable";
	
	public static String ROLE_READER = "library.user.role.reader";
	public static String ROLE_LIBRARIAN = "library.user.role.librarian";
	
}
