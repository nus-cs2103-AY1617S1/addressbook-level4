package seedu.agendum.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.Name;

//@@author A0148095X
public class NameTest {
    private String invalidNameString = "Vishnu \n Rachael \n Weigang";
    private String validNameString = "Justin";
    
    private Name one;
    private Name another;
    
    @Before
    public void setUp() throws IllegalValueException {
        one = new Name(validNameString);
        another = new Name(validNameString);
    }
    
    @Test
    public void equals_symmetric_returnsTrue() throws IllegalValueException {
        assertTrue(one.equals(another) && another.equals(one));
    }
    
    @Test
    public void hashCode_symmetric_returnsTrue() throws IllegalValueException {
        assertTrue(one.hashCode() == another.hashCode());
    }
    
    @SuppressWarnings("unused")
    @Test (expected = IllegalValueException.class)
    public void name_invalid_throwsIllegalValueException() throws IllegalValueException {
        Name name = new Name(invalidNameString);
    }
}
