package seedu.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.EventDuration;

//@@author A0127570H
public class EventDurationTest {

	@Test
	public void equal_noEndTime() throws IllegalValueException {
		EventDuration e1 = new EventDuration("01-01-15","");
		EventDuration e2 = new EventDuration("01-01-15","");
		EventDuration e3 = new EventDuration("01-02-15","");
		String test = "blah";
		
		assertEquals(e1, e2);
		assertFalse(e2.equals(e3));
		assertFalse(e2.equals(null));
		assertFalse(e1.equals(test));
	}
}
