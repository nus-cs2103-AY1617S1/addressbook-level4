package seedu.address.model;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;

//@@author A0142325R

/*
 * Tests for updating out-dated recurring tasks
 */
public class RecurringTest {
    
    @Test
    public void refresh_updateDeadline_success() throws IllegalValueException{
        Deadline d1=new Deadline("01.01.2016");
        d1.updateRecurringDate(2);
        assertEquals("03.01.2016",d1.getValue());
    }
}
