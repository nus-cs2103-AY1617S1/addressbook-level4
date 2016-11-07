package seedu.address.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Priority;

//@@author A0146123R
/**
 * Tests Priority.
 */
public class PriorityTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void addPriority_validPriority_success() throws IllegalValueException {
        Priority p1 = new Priority(0);
        assertEquals("0", p1.toString());
        Priority p2 = new Priority(2);
        assertEquals("2", p2.toString());
        Priority p3 = new Priority(3);
        assertEquals("3", p3.toString());
    }
    
    @Test
    public void addPriority_negativePriority_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Priority(-1);
    }
    
    @Test
    public void addPriority_largerThanThree_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Priority(4);
    }
    
}