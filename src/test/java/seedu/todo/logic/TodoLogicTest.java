package seedu.todo.logic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.logic.parser.Parser;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.Model;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

//@@author A0135817B
public class TodoLogicTest {
    private static final String INPUT = "input";
    private static final String COMMAND = "command";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Mock private Parser parser; 
    @Mock private Dispatcher dispatcher;
    @Mock private ParseResult parseResult;
    @Mock private Model model;
    @Mock private BaseCommand command;
    
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        // Wire up some default behavior 
        when(parser.parse(TodoLogicTest.INPUT))
            .thenReturn(parseResult);
        when(parseResult.getCommand())
            .thenReturn(TodoLogicTest.COMMAND);
        when(dispatcher.dispatch(TodoLogicTest.COMMAND))
            .thenReturn(command);

        logic = new TodoLogic(parser, model, dispatcher);
    }
    
    private CommandResult execute() throws Exception {
        CommandResult r = logic.execute(TodoLogicTest.INPUT);

        verify(parser).parse(TodoLogicTest.INPUT);
        verify(dispatcher).dispatch(TodoLogicTest.COMMAND);
        
        return r;
    }
    
    @Test
    public void testExecute() throws Exception {
        execute();
        
        verify(command).setModel(model);
        verify(command).setArguments(parseResult);
        verify(command).execute();
        
        // Logic should not touch model directly 
        verifyZeroInteractions(model);
    }
    
    @Test
    public void testArgumentError() throws Exception {
        // Create a stub exception for setArguments to throw
        ValidationException e = mock(ValidationException.class);
        ErrorBag errors = mock(ErrorBag.class);
        
        when(e.getErrors()).thenReturn(errors);
        doThrow(e).when(command).setArguments(parseResult);
        
        CommandResult r = execute();
        
        assertFalse(r.isSuccessful());
        // Make sure the command is never executed 
        verify(command, never()).execute();
    }
    
    @Test
    public void testExecuteError() throws Exception {
        // Create a stub exception for execute to throw
        ValidationException e = mock(ValidationException.class);
        ErrorBag errors = mock(ErrorBag.class);

        when(e.getErrors()).thenReturn(errors);
        doThrow(e).when(command).execute();

        CommandResult r = execute();
        
        assertFalse(r.isSuccessful());
        assertEquals(errors, r.getErrors());
    }
    
    @Test
    public void testDispatchError() throws Exception {
        // Create a stub exception for execute to throw
        IllegalValueException e = mock(IllegalValueException.class);
        when(e.getMessage()).thenReturn("Test message");
        doThrow(e).when(dispatcher).dispatch(TodoLogicTest.COMMAND);

        CommandResult r = execute();

        assertFalse(r.isSuccessful());
        assertEquals("Test message", r.getFeedback());
    }
    
    @Test
    public void testEmptyInput() throws Exception {
        CommandResult r = logic.execute("");
        
        assertNotNull(r);
        assertNotNull(r.getFeedback());
        verifyZeroInteractions(parser);
    }
}
