package seedu.address.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;

//@@author A0146123R
/**
 * Tests Deadline and EventDate.
 */
public class DateTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void addDeadline_validDate_success() throws IllegalValueException {
        Deadline d1 = new Deadline("");
        assertEquals("", d1.getValue());
        Deadline d2 = new Deadline("04.11.2016-10");
        assertEquals("04.11.2016-10", d2.getValue());
        Deadline d3 = new Deadline("4 Nov");
        assertEquals("04.11.2016", d3.getValue());
    }
    
    @Test
    public void addDeadline_invalidDate_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Deadline("hi");
    }
    
    @Test
    public void addEventDate_validDate_success() throws IllegalValueException {
        EventDate d1 = new EventDate("4.11.2016", "05.11.2016");
        assertEquals("04.11.2016 to 05.11.2016", d1.getValue());
        EventDate d2 = new EventDate("4 Nov 10am", "4 Nov 11am");
        assertEquals("04.11.2016-10 to 04.11.2016-11", d2.getValue());
    }
    
    @Test
    public void addEventDate_invalidStartDate_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new EventDate("hi", "04.11.2016-11");
    }
    
    @Test
    public void addEventDate_invalidEndDate_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new EventDate("04.11.2016-10", "hi");
    }
     
}
