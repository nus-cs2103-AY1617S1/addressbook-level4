package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.StringArgument;

public class StringArgumentTest {
    
    private Argument<String> arg = new StringArgument("test");

    @Test
    public void testSetValue() throws IllegalValueException {
        arg.setValue("Hello world");
        assertTrue(arg.hasBoundValue());
        assertEquals("Hello world", arg.getValue());
    }
    
    @Test
    public void testTrimValue() throws IllegalValueException {
        arg.setValue("  Hello world ");
        assertTrue(arg.hasBoundValue());
        assertEquals("Hello world", arg.getValue());
    }
    
    @Test
    public void testNotBindEmptyValue() throws IllegalValueException {
        arg.setValue("  ");
        assertTrue(arg.hasBoundValue());
        assertEquals(arg.getValue(), "");
    }
}
