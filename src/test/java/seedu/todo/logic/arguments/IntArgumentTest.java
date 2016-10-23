package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;

//@@author A0135817B
public class IntArgumentTest {
    private Argument<Integer> argument;
    
    private int setInput(String input) throws IllegalValueException {
        argument.setValue(input);
        return argument.getValue();
    }
    
    @Before
    public void setUp() {
        argument = new IntArgument("test");
    }

    @Test
    public void testParse() throws IllegalValueException {
        assertEquals(123, setInput("123"));
        assertEquals(-345, setInput("-345"));
    }
    
    @Test(expected=IllegalValueException.class)
    public void testFloatArgument() throws IllegalValueException {
        setInput("12.34");
    }
    
    @Test(expected=IllegalValueException.class)
    public void testStringArgument() throws IllegalValueException {
        setInput("random stuff");
    }
}
