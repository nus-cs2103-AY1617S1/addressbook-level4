package seedu.task.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.EventDuration;

//@@author A0127570H
public class EventDurationTest {

	@Test
	public void equal_noEndTime() throws IllegalValueException {
		EventDuration e1 = new EventDuration("01-01-15","");
		EventDuration e2 = new EventDuration("01-01-15","");
		EventDuration e3 = new EventDuration("01-02-15","");
		
		assertEquals(e1, e2);
	}
}
