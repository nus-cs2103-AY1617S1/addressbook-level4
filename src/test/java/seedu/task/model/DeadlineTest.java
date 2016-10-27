package seedu.task.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Deadline;

//@@author A0127570H
public class DeadlineTest {

    @Test
    public void deadline_check() throws IllegalValueException {
        Deadline d1 = new Deadline("01-01-15");
        Deadline d2 = new Deadline("01-01-15");
        Deadline d3 = new Deadline("01-02-15");
        Deadline d4 = null;

        assertEquals(d1,d2);
        assertFalse(d1 == d3);
        assertFalse(d1 == d4);
        
    }

}
