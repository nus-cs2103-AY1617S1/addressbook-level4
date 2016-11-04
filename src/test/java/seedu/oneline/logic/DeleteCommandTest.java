package seedu.oneline.logic;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.oneline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.DeleteCommand;
import seedu.oneline.logic.commands.HelpCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;

public class DeleteCommandTest extends LogicTestManager {

    @Test
    public void deleteCommand_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("del", expectedMessage);
    }

    @Test
    public void deleteCommand_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("del");
    }

    @Test
    public void deleteCommand_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("del 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }   

}
