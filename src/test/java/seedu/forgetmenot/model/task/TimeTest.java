package seedu.forgetmenot.model.task;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

public class TimeTest {
    
	//@@author A0139671X
    @Test
    public void isValidDate_trueIfDateFormatIsCorrect() {

        ArrayList<String> validDates = new ArrayList<String>();
        validDates.add("01/1/18");
        validDates.add("1/1/16");
        validDates.add("1/01/16");
        validDates.add("31/10/10");
        validDates.add("30/6/10");
        validDates.add("6/6/10");
        validDates.add("29/2/16");
        validDates.add("01/01/16");

        ArrayList<String> invalidDates = new ArrayList<String>();
        invalidDates.add("32/1/16"); // day out of range
        invalidDates.add("1/13/20"); // month out of range
        invalidDates.add("29/2/17"); // 2017 not a leap year
        invalidDates.add("30/2/16"); // February has max 29 days even during leap year
        invalidDates.add("31/6/16"); // June 30 days
        invalidDates.add("111/2/16"); // invalid day
        invalidDates.add("22/2/2008"); // invalid year
        invalidDates.add("29/a/20"); // invalid month

        for (int i = 0; i < validDates.size(); i++)
            assertTrue(Time.isValidDate(validDates.get(i)));

        for (int i = 0; i < invalidDates.size(); i++)
            assertFalse(Time.isValidDate(invalidDates.get(i)));
    }
    
    //@@author A0147619W
	@Test
	public void parseTime_invalidTimes_throwsError() throws IllegalValueException {
		
		assertInvalidTime("tdy");
		assertInvalidTime("46pm");
		assertInvalidTime("invalid");
		assertInvalidTime("abc5pm");
		assertInvalidTime("!@#");
	}
	
	@Test
	public void checkOrderOfDates_validStartAndEndTimes_trueIfStartTimeIsBeforeEndTime() throws IllegalValueException {
		
		assertTrue(Time.checkOrderOfDates("today", "tmr"));
		assertTrue(Time.checkOrderOfDates("today", "day after tmr"));
		assertTrue(Time.checkOrderOfDates("tmr", "5 days later"));
		assertTrue(Time.checkOrderOfDates("7pm", "8pm"));
		assertTrue(Time.checkOrderOfDates("2 days after the next tuesday", "1 week after the next tuesday"));
		assertTrue(Time.checkOrderOfDates("28/2/17", "1/3/17"));
		assertTrue(Time.checkOrderOfDates("christmas", "new year"));
		assertTrue(Time.checkOrderOfDates("today 11:59pm", "1 week later"));
//		assertTrue(Time.checkOrderOfDates("today", "")); // If the end time is missing, it is still considered valid
//		assertTrue(Time.checkOrderOfDates("", "tmr")); // If the start time is missing, it is still considered valid
//		assertTrue(Time.checkOrderOfDates("", "")); // If both start time and end time is missing, it is still considered valid
		
	}
	
	@Test
	public void checkOrderOfDates_invalidStartAndEndTimes_falseIfStartTimeIsAfterEndTime() throws IllegalValueException {
		
		assertFalse(Time.checkOrderOfDates("tmr", "today"));
		assertFalse(Time.checkOrderOfDates("10 mins after today", "today"));
		assertFalse(Time.checkOrderOfDates("4/4/20", "3 days later"));
		assertFalse(Time.checkOrderOfDates("2 days after next mon", "next tue"));
		assertFalse(Time.checkOrderOfDates("next month", "next week"));
		assertFalse(Time.checkOrderOfDates("christmas", "christmas eve"));
		
	}

	private void assertInvalidTime(String time) {
		try {
			Time checkTime = new Time(time);
			System.out.println("Able to print correct time" + checkTime.easyReadDateFormatForUI());
			fail("didn't throw exception");
		} catch (IllegalValueException e) {
			assertEquals(e.getMessage(), Time.MESSAGE_TIME_CONSTRAINTS);
		}
	}
    
}
