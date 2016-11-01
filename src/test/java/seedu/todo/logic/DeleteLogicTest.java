//@@author A0093896H
package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.DeleteCommand;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todo.testutil.TestDataHelper;

/**
 * Test class for the delete command's logic
 */
public class DeleteLogicTest extends CommandLogicTest {

    @Before
    public void delete_setup() {}
    
    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand(DeleteCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand(DeleteCommand.COMMAND_WORD);
    }

    @Test
    public void execute_delete_removesCorrectTask() throws IllegalValueException, TaskNotFoundException {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        DoDoBird expectedAB = helper.generateToDoList(threeTasks);
        expectedAB.deleteTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1).getName()),
                expectedAB,
                expectedAB.getTaskList());
    }
}
