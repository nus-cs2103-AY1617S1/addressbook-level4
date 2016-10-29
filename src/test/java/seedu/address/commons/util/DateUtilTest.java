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
	SimpleDateFormat dateFormatWithTime = DateUtil.dateFormatWithTime;
	
	/**
	 * Testing areValidWords
	 */
	@Test
	public void areValidWords_validWords() {
		assertTrue(DateUtil.areValidWords("today"));
		assertTrue(DateUtil.areValidWords("tomorrow"));
		assertTrue(DateUtil.areValidWords("tmr"));
		assertTrue(DateUtil.areValidWords("the day after tmr"));
		assertTrue(DateUtil.areValidWords("Monday"));
		assertTrue(DateUtil.areValidWords("next Monday"));
		assertTrue(DateUtil.areValidWords("this Friday"));
		assertTrue(DateUtil.areValidWords("1st January"));
		assertTrue(DateUtil.areValidWords("2nd January"));
		assertTrue(DateUtil.areValidWords("3rd March"));
		assertTrue(DateUtil.areValidWords("4th May"));
		assertTrue(DateUtil.areValidWords("5th June"));
		assertTrue(DateUtil.areValidWords("11th July"));
		assertTrue(DateUtil.areValidWords("12th September"));
		assertTrue(DateUtil.areValidWords("21st September"));
		assertTrue(DateUtil.areValidWords("22nd September"));
		assertTrue(DateUtil.areValidWords("23rd September"));
		assertTrue(DateUtil.areValidWords("31 Oct 2016"));
		assertTrue(DateUtil.areValidWords("31 Oct"));
		assertTrue(DateUtil.areValidWords("5pm"));
		assertTrue(DateUtil.areValidWords("5:00"));
		assertTrue(DateUtil.areValidWords("12:34pm"));
		assertTrue(DateUtil.areValidWords("1.00am"));
		assertTrue(DateUtil.areValidWords("31 Oct 5pm"));
	}
	
	@Test
	public void areValidWords_invalidWords() {
		assertFalse(DateUtil.areValidWords("")); // Empty string
		assertFalse(DateUtil.areValidWords("tday")); // Spelling errors
		assertFalse(DateUtil.areValidWords("tomorow"));
		assertFalse(DateUtil.areValidWords("-1.00")); // Negative values
		assertFalse(DateUtil.areValidWords("23.60")); // MM exceeded 59
		assertFalse(DateUtil.areValidWords("24.00")); // HH exceeded 23
		assertFalse(DateUtil.areValidWords("12.300")); // MM supposed to have 2 digits at most
		assertFalse(DateUtil.areValidWords("230.59")); // HH supposed to have 2 digits at most
	}
	
	/**
	 * Testing isValidTimeFormat
	 */
	@Test
	public void isValidTimeFormat_validTimeFormats() {
		assertTrue(DateUtil.isValidTimeFormat("23.59"));
		assertTrue(DateUtil.isValidTimeFormat("00.00"));
		assertTrue(DateUtil.isValidTimeFormat("12.30"));
		assertTrue(DateUtil.isValidTimeFormat("23:59"));
		assertTrue(DateUtil.isValidTimeFormat("00:00"));
		assertTrue(DateUtil.isValidTimeFormat("12:30"));
		
		assertTrue(DateUtil.isValidTimeFormat("1pm"));
		assertTrue(DateUtil.isValidTimeFormat("12am"));
		assertTrue(DateUtil.isValidTimeFormat("12:04am"));
		assertTrue(DateUtil.isValidTimeFormat("12.04pm"));
		assertTrue(DateUtil.isValidTimeFormat("5:37pm"));
		assertTrue(DateUtil.isValidTimeFormat("0.23pm"));
	}
	
	@Test
	public void isValidTimeFormat_invalidTimeFormats() {
		assertFalse(DateUtil.isValidTimeFormat("23.60")); // MM exceeded 59
		assertFalse(DateUtil.isValidTimeFormat("24.00")); // HH exceeded 23
		assertFalse(DateUtil.isValidTimeFormat("12.300")); // MM supposed to have 2 digits at most
		assertFalse(DateUtil.isValidTimeFormat("230:59")); // HH supposed to have 2 digits at most
		
		assertFalse(DateUtil.isValidTimeFormat("1p")); // Supposed to be pm
		assertFalse(DateUtil.isValidTimeFormat("12a")); // Supposed to be am
		assertFalse(DateUtil.isValidTimeFormat("12:94am")); // MM exceeded 59
		assertFalse(DateUtil.isValidTimeFormat("24.04pm")); // HH exceeded 23
	}
	
	/**
	 * Testing isWithinMinutesRange
	 */
	@Test
	public void isWithinMinutesRange_withinRange() {
		assertTrue(DateUtil.isWithinMinutesRange("0")); // Lower bound
		assertTrue(DateUtil.isWithinMinutesRange("1"));
		assertTrue(DateUtil.isWithinMinutesRange("59")); // Upper bound
		assertTrue(DateUtil.isWithinMinutesRange("58"));
		assertTrue(DateUtil.isWithinMinutesRange("30"));
	}
	
	@Test
	public void isWithinMinutesRange_outsideRange() {
		assertFalse(DateUtil.isWithinMinutesRange(null)); // Null
		assertFalse(DateUtil.isWithinMinutesRange(""));   // Empty string
		assertFalse(DateUtil.isWithinMinutesRange("a"));  // Non-integer
		assertFalse(DateUtil.isWithinMinutesRange("-1")); // Close to lower boundary
		assertFalse(DateUtil.isWithinMinutesRange("-2"));
		assertFalse(DateUtil.isWithinMinutesRange("60")); // Close to upper boundary
		assertFalse(DateUtil.isWithinMinutesRange("61"));
		assertFalse(DateUtil.isWithinMinutesRange("1000"));
		assertFalse(DateUtil.isWithinMinutesRange("-1000"));
	}
	
	/**
	 * Testing isWithinHoursRange
	 */
	@Test
	public void isWithinHoursRange_withinRange() {
		assertTrue(DateUtil.isWithinHoursRange("0")); // Lower bound
		assertTrue(DateUtil.isWithinHoursRange("1"));
		assertTrue(DateUtil.isWithinHoursRange("23")); // Upper bound
		assertTrue(DateUtil.isWithinHoursRange("22"));
		assertTrue(DateUtil.isWithinHoursRange("12"));
	}
	
	@Test
	public void isWithinHoursRange_outsideRange() {
		assertFalse(DateUtil.isWithinHoursRange(null)); // Null
		assertFalse(DateUtil.isWithinHoursRange(""));   // Empty string
		assertFalse(DateUtil.isWithinHoursRange("a"));  // Non-integer
		assertFalse(DateUtil.isWithinHoursRange("-1")); // Close to lower boundary
		assertFalse(DateUtil.isWithinHoursRange("-2"));
		assertFalse(DateUtil.isWithinHoursRange("24")); // Close to upper boundary
		assertFalse(DateUtil.isWithinHoursRange("25"));
		assertFalse(DateUtil.isWithinHoursRange("1000"));
		assertFalse(DateUtil.isWithinHoursRange("-1000"));
	}
	
	/**
	 * Testing retrieveDateTime
	 */
	@Test
	public void retrieveDateTime() {
		Date toRetrieveDate = new GregorianCalendar(2016, Calendar.OCTOBER, 1, 13, 30).getTime();  // 1 October 2016 13:30
		Date toRetrieveTime = new GregorianCalendar(2017, Calendar.JANUARY, 20, 16, 20).getTime(); // 20 January 2017 16:20
		Date expected = new GregorianCalendar(2016, Calendar.OCTOBER, 1, 16, 20).getTime(); 	   // 1 October 2016 16:20
		Date actual = DateUtil.retrieveDateTime(toRetrieveDate, toRetrieveTime);
		assertTrue(actual.equals(expected));
		
		toRetrieveDate = new GregorianCalendar(2020, Calendar.AUGUST, 31, 0, 0).getTime();     // 31 August 2020 00:00
		toRetrieveTime = new GregorianCalendar(2010, Calendar.SEPTEMBER, 1, 23, 59).getTime(); // 1 September 2010 23:59
		expected = new GregorianCalendar(2020, Calendar.AUGUST, 31, 23, 59).getTime(); 	   	   // 31 August 2020 23:59
		actual = DateUtil.retrieveDateTime(toRetrieveDate, toRetrieveTime);
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * Testing isValidDateFormat
	 */
	@Test
	public void isValidDateFormat_validFormats() {
		// Absolute dates
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
		
		// Relative dates
		assertTrue(DateUtil.isValidDateFormat("today"));
		assertTrue(DateUtil.isValidDateFormat("tomorrow"));
		assertTrue(DateUtil.isValidDateFormat("tmr"));
		assertTrue(DateUtil.isValidDateFormat("the day after tomorrow"));
		assertTrue(DateUtil.isValidDateFormat("Monday"));
		assertTrue(DateUtil.isValidDateFormat("next Monday"));
		assertTrue(DateUtil.isValidDateFormat("this Friday"));
	}
	
	@Test
	public void isValidDateFormat_invalidFormats() {
		// Absolute dates
		// Many variations of an invalid date format	
		assertTrue(DateUtil.isValidDateFormat("2016 October 31")); // Year cannot be placed at the front
		assertTrue(DateUtil.isValidDateFormat("2016October31"));
		assertFalse(DateUtil.isValidDateFormat("Oct 2016 31")); // Year cannot be placed at the centre
		assertFalse(DateUtil.isValidDateFormat("32 Oct 2016")); // October has 31 days
		assertFalse(DateUtil.isValidDateFormat("Oct 32 2016")); // October has 31 days
		assertFalse(DateUtil.isValidDateFormat("Oct 2016")); // No day included
		
		// Relative dates
		assertFalse(DateUtil.isValidDateFormat("tdy")); // Spelling mistakes
		assertFalse(DateUtil.isValidDateFormat("tomorow"));
		assertFalse(DateUtil.isValidDateFormat("next Tuesd"));
	}
	
	/**
	 * Testing isValidStartDateToEndDateFormat
	 */
	@Test
	public void isValidStartDateToEndDateFormat_validFormats() {
		// Absolute dates
		// Many variations of a valid start date to end date format
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 1 Nov"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 to 1 Nov")); // Can use either "to" or "-"
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - November 1")); // Flexibility between date formats
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct31-1Nov")); // Spaces can be removed
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct31to1Nov"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("October 31 to 1 November"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("October31to1November"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("OcTobEr31to1NOvembEr")); // Case insensitivity
		
		// Relatives dates
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("tmr to day after tmr"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("tmr - day after tmr"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("tmr to next tues"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("tmr - next tues"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("next tues to next wed"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("next tues - next wed"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("next friday - 21 dec"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("today 5pm to tmr 6pm"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("today 5pm - tmr 6pm"));
		
		// Day/Month/Year inferred for End date
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("tmr 5pm to 6pm"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("21 Nov 5pm - 6pm"));
		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Monday 1am to 3pm"));
	}
	
	@Test
	public void isValidStartDateToEndDateFormat_invalidFormats() {
		// Absolute dates
		// Many variations of an invalid start date to end date format
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - Oct 30")); 	  // End date cannot be earlier than start date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Octo 31 - 1 Nov"));  	  // Spelling mistakes
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 1 Nov 2015")); // End date cannot be earlier than start date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 32 - 2 Nov")); 	  // No 32nd October
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 -- 1 Nov")); 	  // Only 1 "-" allowed		assertTrue(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - 2016 Nov 1")); // Invalid end date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("Oct 31 - Nov 2016 10"));// Invalid end date
		
		// Relative dates
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("tmr to day after tmrw")); // Spelling mistakes
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("tmr to today")); 		   // End date cannot not be earlier than start date
		
		// Day/Month/Year inferred for End date
		assertFalse(DateUtil.isValidStartDateToEndDateFormat("tmr 5pm to 4pm"));  // End date cannot not be earlier than start date
	}
	
	/**
	 * Testing validateDateIsSensible
	 */	
	@Test
	public void validateDateIsSensible_sensibleAndReturnsDate() {
		// Absolute dates
		// Simulating the conditions where Natty returns 1 October 2016
		Date date = new GregorianCalendar(2016, Calendar.OCTOBER, 1).getTime();
		String dateString = "1 Oct";
		assertTrue(DateUtil.isDateSensible(date, dateString));

		dateString = "Oct 1";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		dateString = "1 October 2016";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		dateString = "October 1 2016";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		dateString = "October 1st 2016";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		// Relative dates
		dateString = "tomorrow";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		dateString = "next tuesday";
		assertTrue(DateUtil.isDateSensible(date, dateString));
		
		dateString = "monday";
		assertTrue(DateUtil.isDateSensible(date, dateString));
	}
	
	@Test
	public void validateDateIsSensible_insensibleAndReturnsNull() {
		// Simulating the conditions where Natty returns 1 October 2016
		Date date = new GregorianCalendar(2016, Calendar.OCTOBER, 1).getTime();
		String dateString = "32 Oct";
		assertFalse(DateUtil.isDateSensible(date, dateString));
		
		dateString = "October hello world";
		assertFalse(DateUtil.isDateSensible(date, dateString));
		
		dateString = "Random october november";
		assertFalse(DateUtil.isDateSensible(date, dateString));
	}
	
	/**
	 * Testing getDate
	 */
	@Test
	public void getDate_validFormats() {
		// Absolute dates
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
		
		// With time
		String expectedTime = "31.10.2016 05:30PM";
		assertEquals(dateFormatWithTime.format(DateUtil.getDate("31 Oct 5.30pm")), expectedTime);
		
		// Relative dates
		Date tomorrow = DateUtil.createDateAfterToday(1, 5, 30);
		expected = dateFormat.format(tomorrow);
		assertEquals(dateFormat.format(DateUtil.getDate("tomorrow")), expected);
		
		// With time
		expected = dateFormatWithTime.format(tomorrow);
		assertEquals(dateFormatWithTime.format(DateUtil.getDate("tomorrow 5.30am")), expected);
	}
	
	@Test
	public void getDate_invalidFormats() {
		// Absolute dates
		// Null returned as a result of invalid dates
		assertTrue(DateUtil.getDate("31 Octob 2016") == null); // Spelling mistake for Oct
		assertTrue(DateUtil.getDate("32 Oct 2016") == null);   // October has 31 days
		assertTrue(DateUtil.getDate("Oct 2016 31") == null);   // Year can only be included at the back
		assertTrue(DateUtil.getDate("Oct 2016") == null); 	   // No day included
		assertTrue(DateUtil.getDate("2016") == null); 		   // No month and date included
		assertTrue(DateUtil.getDate("October") == null); 	   // No date included
		assertTrue(DateUtil.getDate("31") == null); 		   // No month included
		
		// Relative dates
		assertTrue(DateUtil.getDate("next tus") == null); 	   // Spelling mistake
	}
	
	/**
	 * Testing getStartAndEndDates
	 */	
	@Test
	public void getStartAndEndDates_validFormats() {
		// Absolute dates
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
		
		actual = DateUtil.getStartAndEndDates("OCtoBEr31to1NOvembEr"); // Case insensitivity
		assertEquals(dateFormat.format(actual[0]), expected1);
		assertEquals(dateFormat.format(actual[1]), expected2);
		
		// Relative dates
		Date firstDate = DateUtil.createDateAfterToday(1, 17, 30);		// Tomorrow 5.30pm
		Date secondDate = DateUtil.createDateAfterToday(2, 20, 20); 	// The day after tomorrow 8.20pm
		
		actual = DateUtil.getStartAndEndDates("tmr 5.30pm to day after tmr 8.20pm");
		expected1 = dateFormatWithTime.format(firstDate);
		expected2 = dateFormatWithTime.format(secondDate);
		assertEquals(dateFormatWithTime.format(actual[0]), expected1);
		assertEquals(dateFormatWithTime.format(actual[1]), expected2);
	}
	
	@Test
	public void getStartAndEndDates_invalidFormats() {
		// Absolute dates
		Date[] actual = DateUtil.getStartAndEndDates("Octo 31 - 1 Nov"); // Spelling errors
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("Oct 32 - 2 Nov"); // No 32nd October
		assertTrue(actual == null);
		
		actual = DateUtil.getStartAndEndDates("Oct 31 -- 1 Nov"); // Only 1 "-" allowed
		assertTrue(actual == null);
		
		// Relative dates
		actual = DateUtil.getStartAndEndDates("tmr to today"); // End date cannot not be earlier than start date
		assertTrue(actual == null);
	}
}
