package seedu.forgetmenot.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

public class DatePreParseTest {
	
	@Test
	public void preparse_correctPreParse_returnsPreParsedFormat() throws IllegalValueException {
		
		String expectedDate = "10/31/16";
		assertEquals(expectedDate, DatePreParse.preparse("31/10/16"));
		
		String notExpectedDate = "2/3/16";
		assertNotEquals(notExpectedDate, DatePreParse.preparse("2/3/16"));
	}

}
