package seedu.address.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateUtilTest {

	// Format for displaying dates
	SimpleDateFormat dateFormat = DateUtil.dateFormat;
	
	/**
	 * Testing isValidDateFormat
	 */
	@Test
	public void isValidDateFormat_validFormats() {
		// Many variations of a valid date format
		assertTrue(DateUtil.isValidDateFormat("Oct 31"));
		assertTrue(DateUtil.isValidDateFormat("OcT 31")); // Case insensitivity
		assertTrue(DateUtil.isValidDateFormat("Oct31")); // Spaces can be removed for <day> <month> formats
		assertTrue(DateUtil.isValidDateFormat("31 Oct"));
		assertTrue(DateUtil.isValidDateFormat("31Oct"));
		assertTrue(DateUtil.isValidDateFormat("October 31"));
		assertTrue(DateUtil.isValidDateFormat("OcToBer 31"));
		assertTrue(DateUtil.isValidDateFormat("October31"));
		assertTrue(DateUtil.isValidDateFormat("31 October"));
		assertTrue(DateUtil.isValidDateFormat("31October"));
		assertTrue(DateUtil.isValidDateFormat("31 Oct 2016")); // Year can be included but only at the back
		assertTrue(DateUtil.isValidDateFormat("31Oct2016")); // Spaces can be removed for <day> <month> <year> formats but not <month> <day> <year> formats
		assertTrue(DateUtil.isValidDateFormat("Oct 31 2016")); // Year can be included but only at the back
	}
	
	@Test
	public void isValidDateFormat_invalidFormats() {
		// Many variations of an invalid date format	
		assertTrue(DateUtil.isValidDateFormat("2016 October 31")); // Year cannot be placed at the front
		assertTrue(DateUtil.isValidDateFormat("2016October31"));
		assertFalse(DateUtil.isValidDateFormat("Oct 2016 31")); // Year cannot be placed at the centre
		assertFalse(DateUtil.isValidDateFormat("32 Oct 2016")); // October has 31 days
		assertFalse(DateUtil.isValidDateFormat("Oct 32 2016")); // October has 31 days
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
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 -- 1 Nov")); // Only 1 "-" allowed		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 2016 Nov 1")); // Invalid end date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - Nov 2016 10")); // Invalid end date
	}
	
	/**
	 * Testing getDate
	 */
	@Test
	public void getDate_validFormats() {
		String expected = "31.10.2016";
		assertEquals(dateFormat.format(DateUtil.getDate("Oct 31")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("OcT 31")), expected); // Case insensitivity
		assertEquals(dateFormat.format(DateUtil.getDate("Oct31")), expected); // Spaces can be removed
		assertEquals(dateFormat.format(DateUtil.getDate("31 Oct")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("31Oct")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("October 31")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("OcToBer 31")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("October31")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("31 October")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("31October")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("31 Oct 2016")), expected); // Year can be included but only at the back
		assertEquals(dateFormat.format(DateUtil.getDate("31Oct2016")), expected);
		assertEquals(dateFormat.format(DateUtil.getDate("October 31 2016")), expected); // Year can be included but only at the back
		assertEquals(dateFormat.format(DateUtil.getDate("Oct 31 2016")), expected);
	}
	
	@Test
	public void getDate_invalidFormats() {
		// Null returned as a result of invalid dates
		assertTrue(DateUtil.getDate("31 Octob 2016") == null); // Spelling mistake for Oct
		assertTrue(DateUtil.getDate("32 Oct 2016") == null); // October has 31 days
		assertTrue(DateUtil.getDate("Oct 2016 31") == null); // Year can only be included at the back
		assertTrue(DateUtil.getDate("Oct 2016") == null);  // No day included
		assertTrue(DateUtil.getDate("2016") == null); // No month and date included
		assertTrue(DateUtil.getDate("October") == null); // No date included
		assertTrue(DateUtil.getDate("31") == null); // No month included
	}
	
	/**
	 * Testing validateDateIsSensible
	 */	
	@Test
	public void validateDateIsSensible_sensibleAndReturnsDate() {
		// Simulating the conditions where Natty returns 1 October 2016
		Date date = new GregorianCalendar(2016, Calendar.OCTOBER, 1).getTime();
		String dateString = "1 Oct";
		assertEquals(date, DateUtil.validateDateIsSensible(date, dateString));

		dateString = "Oct 1";
		assertEquals(date, DateUtil.validateDateIsSensible(date, dateString));
		
		dateString = "1 October 2016";
		assertEquals(date, DateUtil.validateDateIsSensible(date, dateString));
		
		dateString = "October 1 2016";
		assertEquals(date, DateUtil.validateDateIsSensible(date, dateString));
		
		dateString = "October 1st 2016";
		assertEquals(date, DateUtil.validateDateIsSensible(date, dateString));
		
	}
	
	@Test
	public void validateDateIsSensible_insensibleAndReturnsNull() {
		// Simulating the conditions where Natty returns 1 October 2016
		Date date = new GregorianCalendar(2016, Calendar.OCTOBER, 1).getTime();
		String dateString = "32 Oct";
		assertTrue(DateUtil.validateDateIsSensible(date, dateString) == null);
		
		dateString = "October hello world";
		assertTrue(DateUtil.validateDateIsSensible(date, dateString) == null);
		
		dateString = "Random october november";
		assertTrue(DateUtil.validateDateIsSensible(date, dateString) == null);
	}
	
	/**
	 * Testing getStartAndEndDates
	 */	
	@Test
	public void getStartAndEndDates_validFormats() {
		String expected1 = "31.10.2016";
		String expected2 = "01.11.2016";
		Date[] actual = DateUtil.getStartAndEndDates("Oct 31 - 1 Nov");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);

		actual = DateUtil.getStartAndEndDates("Oct 31 to 1 Nov"); // Can use either "to" or "-"
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);

		actual = DateUtil.getStartAndEndDates("Oct 31 - November 1"); // Flexibility between date formats
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("Oct 31 - 1 November 2016");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("Oct 31 - November 1 2016");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("Oct31-1Nov"); // Spaces can be removed
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("Oct31to1Nov");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("October 31 to 1 November");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("October31to1November");
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		actual = DateUtil.getStartAndEndDates("OcTobEr31to1NOvembEr"); // Case insensitivity
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
	}
	
	@Test
	public void getStartAndEndDates_invalidFormats() {
		Date[] actual = DateUtil.getStartAndEndDates("Octo 31 - 1 Nov"); // Spelling errors
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("Oct 32 - 2 Nov"); // No 32nd October
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("Oct 31 -- 1 Nov"); // Only 1 "-" allowed
		assertTrue(actual == null);
	}
}
