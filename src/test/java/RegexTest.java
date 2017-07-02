

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class RegexTest {
	
	@Test
	public void testRegex() {
		String pass = "librarian12";

		boolean actual = pass.matches("^[\\d\\w]{8,14}$");
		boolean expected = true;

		assertEquals(expected, actual);
	}

}
