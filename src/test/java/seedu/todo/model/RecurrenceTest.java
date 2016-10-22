package seedu.todo.model;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Rule;

import static org.junit.Assert.assertFalse;

public class RecurrenceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void test_isValidRecurrenceDesc() throws IllegalValueException {
        Recurrence recurrence = new Recurrence(null);
        
        String[] validInputs = {"every monday", "every week", "every year", "everyday", null};
        String[] invalidInputs = {"tomorrow", "randome", "every happy hour"};
        
        
        for (String input : validInputs) {
            assertTrue(recurrence.isValidRecurrenceDesc(input));
        }
        
        for (String input : invalidInputs) {
            assertFalse(recurrence.isValidRecurrenceDesc(input));
        }
    }
        
   
    
}
