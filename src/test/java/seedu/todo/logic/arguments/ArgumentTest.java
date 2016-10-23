package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;

//@@author A0135817B
public class ArgumentTest {
    private Argument<String> arg = new TestArgument();

    @Test
    public void testRequiredErrorMessage() {
        arg.required("Hello world");
        
        try {
            arg.checkRequired();
        } catch (IllegalValueException e) {
            assertEquals("Hello world", e.getMessage());
        }
    }
    
    @Test(expected=IllegalValueException.class)
    public void testRequired() throws IllegalValueException {
        arg.required(); 
        arg.checkRequired();
    }
    
    @Test
    public void testIsOptional() {
        assertTrue(arg.isOptional());
        arg.required();
        assertFalse(arg.isOptional());
    }
    
    @Test
    public void testIsPositional() {
        assertTrue(arg.isPositional());
        arg.flag("t");
        assertFalse(arg.isPositional());
    }
    
    @Test
    public void testFlag() {
        assertNull(arg.getFlag());
        arg.flag("h");
        assertEquals("h", arg.getFlag());
        arg.flag(" H   ");
        assertEquals("h", arg.getFlag());
    }
    
    @Test
    public void testDescription() {
        assertNull(arg.getDescription());
        arg.description("Hello World");
        assertEquals("Hello World", arg.getDescription());
    }
    
    @Test
    public void testToString() {
        assertEquals("[Test]", arg.toString());
        assertEquals("[Something]", arg.toString("Something"));
        
        arg.flag("t");
        assertEquals("[/t Test]", arg.toString());
        assertEquals("[/t Something]", arg.toString("Something"));
        
        arg.required(); 
        assertEquals("/t Test", arg.toString());
        assertEquals("/t Something", arg.toString("Something"));
    }
    
    private class TestArgument extends Argument<String> {
        public TestArgument() {
            super("Test");
        }
    }
}
