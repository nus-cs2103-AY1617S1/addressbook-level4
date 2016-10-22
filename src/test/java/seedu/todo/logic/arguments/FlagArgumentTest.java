package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;

//@@author A0135817B
public class FlagArgumentTest {
    
    private Argument<Boolean> argument;

    @Before
    public void setUp() throws Exception {
        argument = new FlagArgument("test");
    }

    @Test
    public void testDefaultValue() {
        assertEquals("t", argument.getFlag());
        assertFalse(argument.getValue());
        
        argument = new FlagArgument("Pin", true);
        assertTrue(argument.getValue());
        assertEquals("p", argument.getFlag());
    }
    
    @Test
    public void testSetEmptyValue() throws IllegalValueException {
        argument.setValue("");
        assertTrue(argument.getValue());
    }

    @Test
    public void testSetStringValue() throws IllegalValueException {
        argument.setValue("Hello World");
        assertTrue(argument.getValue());
    }
    
    @Test
    public void testToString() {
        argument.flag("t");
        assertEquals("[/t]", argument.toString());
        
        argument.required();
        assertEquals("/t", argument.toString());
    }

}
