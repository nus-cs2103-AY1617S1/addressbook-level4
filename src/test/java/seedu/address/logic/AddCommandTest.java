package seedu.address.logic;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AddCommandTest {
		
	@Test
	public void testIsEventObjectCreatedSuccessfully() throws IllegalValueException {
		ArrayList<String> details = new ArrayList<String>();
		
		details.add("event");
		details.add("project meeting");
		details.add("10-10-2016");
		details.add("1400");
		details.add("10-10-2016");
		details.add("1800");
		details.add("celebrate");
		
		AddCommand test = new AddCommand(details);
		
		assertEquals(details.get(1), test.eventStub.name);
		assertEquals(details.get(2), test.eventStub.startDate);
		assertEquals(details.get(3), test.eventStub.startTime);
		assertEquals(details.get(4), test.eventStub.endDate);
		assertEquals(details.get(5), test.eventStub.endTime);
		assertEquals(details.get(6), test.eventStub.notes);
	}
	
	
}
