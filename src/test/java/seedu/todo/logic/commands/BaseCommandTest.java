package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@@author A0135817B
public class BaseCommandTest extends CommandTest {
    private Argument<String> requiredArgument = mock(StringArgument.class);
    private Argument<Boolean> flagArgument = mock(FlagArgument.class);
    private Argument<Integer> intArgument = mock(IntArgument.class);
    private Argument<String> stringArgument = mock(StringArgument.class);
    
    private StubCommand stubCommand;

    @Override
    protected BaseCommand commandUnderTest() {
        stubCommand = new StubCommand();
        return stubCommand;
    }
    
    @Before
    public void setUp() throws Exception {
        when(requiredArgument.isPositional()).thenReturn(true);
        when(requiredArgument.toString()).thenReturn("required");
        
        when(flagArgument.isOptional()).thenReturn(true);
        when(flagArgument.getFlag()).thenReturn("f");
        when(flagArgument.toString()).thenReturn("flag");
        
        when(intArgument.isOptional()).thenReturn(true);
        when(intArgument.getFlag()).thenReturn("i");
        when(intArgument.toString()).thenReturn("int");
        
        when(stringArgument.isOptional()).thenReturn(true);
        when(stringArgument.getFlag()).thenReturn("s");
        when(stringArgument.toString()).thenReturn("string");
    }
    
    @Test
    public void testSetParameter() throws Exception {
        this.setParameter("required")
            .setParameter("f", "")
            .setParameter("i", "20")
            .setParameter("s", "Hello World");
        
        execute(true);
        
        verify(requiredArgument).setValue("required");
        verify(flagArgument).setValue("");
        verify(intArgument).setValue("20");
        verify(stringArgument).setValue("Hello World");
    }
    
    @Test
    public void testCustomArgumentError() throws Exception {
        command = new CommandWithOverrideMethods();
        
        try {
            execute(false);
            fail();
        } catch (ValidationException e) {
            assertEquals("Test error message", e.getMessage());
            assertTrue(e.getErrors().getNonFieldErrors().contains("Test error"));
        }
    }
    
    @Test
    public void getArgumentSummary() {
        assertEquals("required flag int string", stubCommand.getArgumentSummaryResult());
    }

    @Test(expected=ValidationException.class)
    public void testMissingRequiredArgument() throws Exception {
        IllegalValueException e = mock(IllegalValueException.class);
        doThrow(e).when(requiredArgument).checkRequired();
        
        execute(false);
    }

    private class StubCommand extends BaseCommand {
        @Override
        protected Parameter[] getArguments() {
            return new Parameter[]{ requiredArgument, flagArgument, intArgument, stringArgument };
        }

        @Override
        public String getCommandName() {
            return "stub";
        }

        @Override
        public List<CommandSummary> getCommandSummary() {
            return ImmutableList.of(mock(CommandSummary.class));
        }

        @Override
        public CommandResult execute() throws ValidationException {
            // Does nothing
            return new CommandResult("Great Success!");
        }
        
        public String getArgumentSummaryResult() {
            return getArgumentSummary();
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
