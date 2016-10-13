package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class RecurrenceTest {

    @Test
    public void defaultConstructor_noInput_returnsNoRecurrence() {
        Recurrence noRecurrence = new Recurrence();
        
        assertFalse(noRecurrence.hasRecurrence());
        assertEquals(Recurrence.Pattern.NONE, noRecurrence.getPattern());
        assertEquals(0, noRecurrence.getFrequency());
    }
    
    @Test (expected = AssertionError.class)
    public void constructor_noPatternValidFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidPattern = new Recurrence(Recurrence.Pattern.NONE, 1);
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_negativeFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidFrequency = new Recurrence(Recurrence.Pattern.DAILY, -1);
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_zeroFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidFrequency = new Recurrence(Recurrence.Pattern.DAILY, 0);
    }
    
    @Test
    public void constructor_validPatternFrequency_returnsCorrectRecurrence() throws IllegalValueException {
        Recurrence.Pattern pattern = Recurrence.Pattern.WEEKLY;
        int frequency = 1;
        
        Recurrence recurrence = new Recurrence(pattern, frequency);
        assertTrue(recurrence.hasRecurrence());
        assertEquals(pattern, recurrence.getPattern());
        assertEquals(frequency, recurrence.getFrequency());
    }
    
    @Test
    public void toString_noRecurrence_returnsNoRecurrence() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();
        
        assertEquals(Recurrence.TO_STRING_NO_RECURRENCE, noRecurrence.toString());
    }
    
    @Test
    public void toString_recurrence_returnsCorrectValues() throws IllegalValueException {
        Recurrence recurrence = new Recurrence(Recurrence.Pattern.WEEKLY, 5);
        
        assertEquals("WEEKLY [5 time(s)]", recurrence.toString());
    }
    
    @Test
    public void equals_nonRecurrenceObject_returnsFalse() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();
        
        assertFalse(noRecurrence.equals("String"));
    }
}
