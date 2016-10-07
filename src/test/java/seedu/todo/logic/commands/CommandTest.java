package seedu.todo.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import static org.junit.Assert.*;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.TodoList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.storage.MockStorage;

/**
 * Base test case for testing commands. All command tests should extend this class. 
 * Provides a simple interface for setting up command testing as well as a number 
 * of assertions to inspect the model. 
 */
public abstract class CommandTest {
    private class MockParseResult implements ParseResult {
        public String command; 
        public String positional;
        public Map<String, String> named = new HashMap<>();

        @Override
        public String getComand() {
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

    protected TodoList model;
    protected MockStorage storage;
    protected BaseCommand command;
    protected MockParseResult params;
    
    abstract protected BaseCommand commandUnderTest();

    @Before
    public void setUpCommand() throws Exception {
        storage = new MockStorage();
        model = new TodoList(storage);
        params = new MockParseResult();
        command = commandUnderTest();
    }
    
    /**
     * Returns the task at 1-indexed position, mimicking user input
     */
    protected ImmutableTask getTaskAt(int index) {
        return model.getObserveableList().get(index - 1);
    }
    
    protected void assertTotalTaskCount(int size) {
        assertEquals(size, model.getTasks().size());
    }
    
    protected void assertVisibleTaskCount(int size) {
        assertEquals(size, model.getObserveableList().size());
    }
    
    protected void assertTaskExist(ImmutableTask task) {
        if (!model.getTasks().contains(task)) {
            throw new AssertionError("Task not found in model");
        }
    }
    
    protected void assertTaskNotExist(ImmutableTask task) {
        if (model.getTasks().contains(task)) {
            throw new AssertionError("Task found in model");
        }
    }
    
    protected void assertTaskVisible(ImmutableTask task) {
        if (!model.getObserveableList().contains(task)) {
            throw new AssertionError("Task not found in model");
        }
    }
    
    protected void assertTaskNotVisible(ImmutableTask task) {
        if (model.getObserveableList().contains(task)) {
            throw new AssertionError("Task found in model");
        }
    }
    
    /**
     * Sets the positional parameter for command execution
     */
    protected CommandTest setParameter(String positional) {
        params.positional = positional;
        return this;
    }
    
    /**
     * Sets the named argument for command execution
     */
    protected CommandTest setParameter(String flag, String value) {
        params.named.put(flag, value);
        return this;
    }
    
    protected void execute() throws IllegalValueException {
        command.setArguments(params);
        command.setModel(model);
        command.execute();
    }
}
