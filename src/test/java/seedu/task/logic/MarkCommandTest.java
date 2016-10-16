package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.MarkCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Task;

public class MarkCommandTest extends CommandTest{

    @Test
    public void execute_MarkInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }
    
    @Test
    public void execute_MarkIndexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("mark");
    }
    
    @Test
    public void execute_mark_marksCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
        expectedAB.markTask(1);
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("mark 2",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
    }
}
