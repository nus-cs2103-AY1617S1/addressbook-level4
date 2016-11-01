//@@author A0093896H
package seedu.todo.logic;

import java.util.List;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.SeeCommand;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.testutil.TestDataHelper;

/**
 * Test class for the see command's logic
 */
public class SeeLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_list_showsAllTasks() throws IllegalValueException {
        expectedTDL = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedTDL.getTaskList();

        helper.addToModel(model, 2);

        assertCommandBehavior("see",
                SeeCommand.MESSAGE_SUCCESS,
                expectedTDL,
                expectedList);
    }
}
