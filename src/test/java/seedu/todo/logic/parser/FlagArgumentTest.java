package seedu.todo.logic.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;

public class FlagArgumentTest {
    
    private Argument<Boolean> argument;

    @Before
    public void setUp() throws Exception {
        argument = new FlagArgument("test");
    }

    @Test
    public void testDefaultValue() {
        assertFalse(argument.getValue());
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

}
