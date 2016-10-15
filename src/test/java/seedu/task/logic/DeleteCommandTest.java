package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.DeleteEventCommand;
import seedu.task.logic.commands.DeleteTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

public class DeleteCommandTest extends CommandTest {
    public void execute_deleteTaskInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteTaskIndexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("delete -t");
    }
    
    @Test
    public void execute_deleteEventInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteEventIndexNotFound_errorMessageShown() throws Exception {
        assertEventIndexNotFoundBehaviorForCommand("delete -e");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("delete -t 2",
                String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }
    

    @Test
    public void execute_delete_removesCorrectEvent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Event> threeEvents = helper.generateEventList(3);

        TaskBook expectedAB = helper.generateTaskBook_Events(threeEvents);
        expectedAB.removeEvent(threeEvents.get(1));
        helper.addEventToModel(model, threeEvents);

        assertTaskCommandBehavior("delete -e 2",
                String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, threeEvents.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }
}
