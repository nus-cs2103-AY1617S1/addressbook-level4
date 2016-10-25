package seedu.todo.logic.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.*;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.storage.MovableStorage;

//@@author A0135817B
/**
 * Base test case for testing commands. All command tests should extend this class. 
 * Provides a simple interface for setting up command testing as well as a number 
 * of assertions to inspect the model. 
 */
public abstract class CommandTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    protected Model model;
    protected TodoList todolist;
    @Mock protected MovableStorage<ImmutableTodoList> storage;
    @Mock protected ImmutableTodoList storageData;
    protected BaseCommand command;
    protected StubParseResult params;
    protected CommandResult result;
    
    abstract protected BaseCommand commandUnderTest();

    @Before
    public void setUpCommand() throws Exception {
        when(storage.read()).thenReturn(storageData);
        when(storageData.getTasks()).thenReturn(Collections.emptyList());
        
        todolist = new TodoList(storage);
        model = new TodoModel(todolist, storage);
        params = new StubParseResult();
        command = commandUnderTest();
    }
    
    /**
     * Returns the task visible in the model at 1-indexed position, mimicking user input
     */
    protected ImmutableTask getTaskAt(int index) {
        return model.getObservableList().get(index - 1);
    }
    
    /**
     * Asserts that the model has this number of tasks stored in internal storage (visible and not visible)
     */
    
    protected void assertTotalTaskCount(int size) {
        assertEquals(size, todolist.getTasks().size());
    }
    
    /**
     * Asserts that the model has this number of tasks visible
     */
    protected void assertVisibleTaskCount(int size) {
        assertEquals(size, model.getObservableList().size());
    }
    
    /**
     * Asserts that the task exists in memory
     */
    protected void assertTaskExist(ImmutableTask task) {
        if (!todolist.getTasks().contains(task)) {
            throw new AssertionError("Task not found in model");
        }
    }
    

    /**
     * Asserts that the task does not exist in memory
     */
    protected void assertTaskNotExist(ImmutableTask task) {
        if (todolist.getTasks().contains(task)) {
            throw new AssertionError("Task found in model");
        }
    }
    
    /**
     * Asserts that the task is visible to the user through the model
     */
    protected void assertTaskVisible(ImmutableTask task) {
        if (!model.getObservableList().contains(task)) {
            throw new AssertionError("Task is not visible");
        }
    }

    /**
     * Asserts that the task is visible to the user through the model. 
     * This can also mean the task is simply not in memory. Use {@link #assertTaskHidden}
     * to assert that the task exists, but is not visible
     */
    protected void assertTaskNotVisible(ImmutableTask task) {
        if (model.getObservableList().contains(task)) {
            throw new AssertionError("Task is visible");
        }
    }
    
    /**
     * Asserts that the task exists, but is not visible to the user through 
     * the model
     */
    protected void assertTaskHidden(ImmutableTask task) {
        assertTaskExist(task); 
        assertTaskNotVisible(task);
    }

    /**
     * Sets the positional parameter for command execution. Can be chained. 
     */
    protected CommandTest setParameter(String positional) {
        params.positional = positional;
        return this;
    }
    
    /**
     * Sets the named argument for command execution. Can be chained. 
     */
    protected CommandTest setParameter(String flag, String value) {
        params.named.put(flag, value);
        return this;
    }
    
    @Test
    public void testCommonProperties() {
        assertNotNull(command.getArguments());
        assertThat(command.getCommandName(), not(containsString(" ")));
        assertThat(command.getCommandSummary().size(), greaterThan(0));
    }
    
    /**
     * Executes the command
     */
    protected void execute(boolean expectSuccess) throws ValidationException {
        command.setArguments(params);
        command.setModel(model);
        result = command.execute();
        
        assertEquals(expectSuccess, result.isSuccessful());
    }

    private class StubParseResult implements ParseResult {
        public String command;
        public String positional;
        public Map<String, String> named = new HashMap<>();

        @Override
        public String getCommand() {
            return command;
        }

        @Override
        public Optional<String> getPositionalArgument() {
            return Optional.ofNullable(positional);
        }

        @Override
        public Map<String, String> getNamedArguments() {
            return named;
        }
    }
}
