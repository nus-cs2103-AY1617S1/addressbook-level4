package seedu.todo.logic.commands;

import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.TodoModelTest;

//@@author A0135817B
public class UndoCommandTest extends CommandTest {

    @Override
    protected BaseCommand commandUnderTest() {
        return new UndoCommand();
    }

    /**
     * This is an integration test for the redo command. For a more detailed test on the model itself 
     * {@link TodoModelTest#testUndo} and other related tests 
     */
    @Test
    public void testUndo() throws Exception {
        model.add("Test task");
        execute(true);
    }
    
    @Test(expected = ValidationException.class)
    public void testEmptyUndo() throws Exception {
        execute(false);
    }
}
