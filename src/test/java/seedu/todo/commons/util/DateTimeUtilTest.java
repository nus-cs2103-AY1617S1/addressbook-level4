package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.util.DateTimeUtil;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateTimeUtilTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void isValidDateString_test() {
		String[] validFormats = {"8 Oct 2015", "8/12/2014", "8-12-2000", "2/October/2103", "13 March 2013", "4 May 2013"};
		String[] invalidFormats = {"8 O 2015", "8/13/2014", "8/12-2000", "2/Octber/2103", "13 March", "May 2013"};
		for (String validFormat : validFormats) {
			assertTrue(DateTimeUtil.isValidDateString(validFormat));
		}
		for (String invalidFormat : invalidFormats) {
			assertFalse(DateTimeUtil.isValidDateString(invalidFormat));
		}
		
	}
	
	
	@Test
	public void isValidTimeString_test() {
		String[] validFormats = {"13:30", "09:45", "12:34 pm", "12:45 am", "2:45 pm", "2:4 pm", "12:0"};
		String[] invalidFormats = {"33:30", "13:30pm"};
		for (String validFormat : validFormats) {
			assertTrue(DateTimeUtil.isValidTimeString(validFormat));
		}
		for (String invalidFormat : invalidFormats) {
			assertFalse(DateTimeUtil.isValidTimeString(invalidFormat));
		}
		
	}
}
