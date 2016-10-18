package seedu.todo.logic.commands;

import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;

import static org.junit.Assert.*;

//@@author A0135817B
public class RedoCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new RedoCommand();
    }
    
    /**
     * This is an integration test for the redo command. For a more detailed test on the model itself 
     * {@link seedu.todo.model.TodoModelTest#testRedo} and other related tests 
     */
    @Test
    public void testExecute() throws Exception {
        model.add("Test task");
        model.undo();
        execute(true);
        assertEquals("Test task", getTaskAt(1).getTitle());
    }
    
    @Test(expected = ValidationException.class)
    public void testIncorrectExecute() throws Exception {
        execute(false);
    }
}
