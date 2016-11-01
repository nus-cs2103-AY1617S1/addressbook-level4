package seedu.forgetmenot.model.task;

import static org.junit.Assert.*;

import org.junit.Test;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;

public class TimeTest {
	
	@Test
	public void parseTime_invalidTimes_throwsError() throws IllegalValueException {
		
		assertInvalidTime("tdy");
		assertInvalidTime("46pm");
		assertInvalidTime("invalid");
		assertInvalidTime("abc5pm");
		assertInvalidTime("!@#");
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
