package seedu.address.commons.util;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateUtilTest {

	/**
	 * Testing isValidDateFormat
	 */
	@Test
	public void isValidDateFormat_validFormats() {
		// Many variations of a valid date format
		assertTrue(DateUtil.isValidDateFormat("Oct 31"));
		assertTrue(DateUtil.isValidDateFormat("OcT 31")); // Case insensitivity
		assertTrue(DateUtil.isValidDateFormat("Oct31")); // Spaces can be removed
		assertTrue(DateUtil.isValidDateFormat("31 Oct"));
		assertTrue(DateUtil.isValidDateFormat("31Oct"));
		assertTrue(DateUtil.isValidDateFormat("October 31"));
		assertTrue(DateUtil.isValidDateFormat("OcToBer 31"));
		assertTrue(DateUtil.isValidDateFormat("October31"));
		assertTrue(DateUtil.isValidDateFormat("31 October"));
		assertTrue(DateUtil.isValidDateFormat("31October"));
		assertTrue(DateUtil.isValidDateFormat("31 Oct 2016")); // Year can be included but with no ambiguity
		assertTrue(DateUtil.isValidDateFormat("31Oct2016"));
		assertTrue(DateUtil.isValidDateFormat("2016 October 31"));
		assertTrue(DateUtil.isValidDateFormat("2016October31"));
	}
	
	@Test
	public void isValidDateFormat_invalidFormats() {
		// Many variations of an invalid date format
		assertFalse(DateUtil.isValidDateFormat("Oct 31 2016")); // Either <day> <month> <year> or <year> <month> <day>
		assertFalse(DateUtil.isValidDateFormat("Oct 2016 31")); 
		assertFalse(DateUtil.isValidDateFormat("31 Octo 2016")); // Spelling mistake for Oct
		assertFalse(DateUtil.isValidDateFormat("32 Oct 2016")); // October has 31 days
		assertFalse(DateUtil.isValidDateFormat("31 Oct 16")); 	// Ambiguous day vs year
		assertFalse(DateUtil.isValidDateFormat("Oct 2016")); // No day included
	}
	
	/**
	 * Testing isValidStartDateToEndDateFormat
	 */
	@Test
	public void isValidStartDateToEndDateFormat_validFormats() {
		// Many variations of a valid start date to end date format
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 1 Nov"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 to 1 Nov")); // Can use either "to" or "-"
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - November 1")); // Flexibility between date formats
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 2016 November 1"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 2017 November 1")); // Can support an end date on another year
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct31-1Nov")); // Spaces can be removed
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct31to1Nov"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("October 31 to 1 November"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("October31to1November"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("OcTobEr31to1NOvembEr")); // Case insensitivity
	}
	
	@Test
	public void isValidStartDateToEndDateFormat_invalidFormats() {
		// Many variations of an invalid start date to end date format
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - Oct 30")); // End date cannot be earlier than start date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Octo 31 - 1 Nov")); // Spelling errors
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 1 Nov 2015")); // End date cannot be earlier than start date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 32 - 2 Nov")); // No 32nd October
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 -- 1 Nov")); // Only 1 "-" allowed
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 too 1 Nov")); // Misspelling of "to"
	}
	
	/**
	 * Testing retrieval of dates from strings
	 */
	@Test
	public void getDate_validFormats() {
		Date expected = new Date();
		assertEquals(DateUtil.getDate(""), expected);
		
		expected = new Date();
		assertEquals(DateUtil.getDate(""), expected);
		
		expected = new Date();
		assertEquals(DateUtil.getDate(""), expected);
	}
	
	@Test
	public void getDate_invalidFormats() {
		// Null returned as a result of invalid dates
		assertTrue(DateUtil.getDate("") == null);
		assertTrue(DateUtil.getDate("") == null);
		assertTrue(DateUtil.getDate("") == null);
	}
	
	@Test
	public void getStartAndEndDates_validFormats() {
		Date[] expected = new Date[2];
		expected[0] = new Date();
		expected[1] = new Date();
		Date[] actual = DateUtil.getStartAndEndDates("");
		assertEquals(actual[0], expected[0]);
		assertEquals(actual[1], expected[1]);
		
		expected[0] = new Date();
		expected[1] = new Date();
		actual = DateUtil.getStartAndEndDates("");
		assertEquals(actual[0], expected[0]);
		assertEquals(actual[1], expected[1]);
		
		expected[0] = new Date();
		expected[1] = new Date();
		actual = DateUtil.getStartAndEndDates("");
		assertEquals(actual[0], expected[0]);
		assertEquals(actual[1], expected[1]);
	}
	
	@Test
	public void getStartAndEndDates_invalidFormats() {
		Date[] actual = DateUtil.getStartAndEndDates("");
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("");
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("");
		assertTrue(actual == null);
	}
	
	/**
	 * Utility Functions
	 */
	public void assertDatesEqual(Date[] actual, Date[] expected) {
		
	}
}
