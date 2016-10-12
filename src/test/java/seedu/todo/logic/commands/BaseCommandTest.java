package seedu.todo.logic.commands;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BaseCommandTest extends CommandTest {
    private Argument<String> requiredArgument = mock(StringArgument.class);
    private Argument<Boolean> flagArgument = mock(FlagArgument.class);
    private Argument<Integer> intArgument = mock(IntArgument.class);
    private Argument<String> stringArgument = mock(StringArgument.class);
    
    private StubCommand testCommand;

    @Override
    protected BaseCommand commandUnderTest() {
        this.testCommand = new StubCommand(); 
        return this.testCommand;
    }
    
    @Before
    public void setUp() throws Exception {
        when(requiredArgument.isPositional()).thenReturn(true);
        
        when(flagArgument.isOptional()).thenReturn(true);
        when(flagArgument.getFlag()).thenReturn("f");
        
        when(intArgument.isOptional()).thenReturn(true);
        when(intArgument.getFlag()).thenReturn("i");
        
        when(stringArgument.isOptional()).thenReturn(true);
        when(stringArgument.getFlag()).thenReturn("s");
    }
    
    @Test
    public void testSetParameter() throws Exception {
        this.setParameter("required")
            .setParameter("f", "")
            .setParameter("i", "20")
            .setParameter("s", "Hello World");
        
        execute();
        
        verify(requiredArgument).setValue("required");
        verify(flagArgument).setValue("");
        verify(intArgument).setValue("20");
        verify(stringArgument).setValue("Hello World");
    }
    
    @Test
    public void testCustomArgumentError() throws Exception {
        command = new CommandWithOverrideMethods();
        boolean exceptionThrown = false; 
        
        try {
            execute();
        } catch (ValidationException e) {
            exceptionThrown = true;
            assertEquals("Test error message", e.getMessage());
            assertTrue(e.getErrors().getNonFieldErrors().contains("Test error"));
        }
        
        assertTrue(exceptionThrown);
    }

    @Test(expected=ValidationException.class)
    public void testMissingRequiredArgument() throws Exception {
        IllegalValueException e = mock(IllegalValueException.class);
        doThrow(e).when(requiredArgument).checkRequired();
        
        execute();
    }
    
    @Test(expected=IllegalValueException.class)
    public void testInvalidGetTaskAt() throws Exception {
        execute();
        testCommand.accessInvalidIndex();
    }

    private class StubCommand extends BaseCommand {
        @Override
        protected Parameter[] getArguments() {
            return new Parameter[]{ requiredArgument, flagArgument, intArgument, stringArgument };
        }

        @Override
        public CommandResult execute() throws IllegalValueException {
            // Does nothing
            return null;
        }

        public void accessInvalidIndex() throws IllegalValueException {
            getTaskAt(100);
        }
    }
    
    private class CommandWithOverrideMethods extends StubCommand {
        @Override
        protected String getArgumentErrorMessage() {
            return "Test error message";
        }

        @Override
        protected void validateArguments() {
            errors.put("Test error");
        }
    }
}
