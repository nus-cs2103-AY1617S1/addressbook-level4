package tars.logic;

import static tars.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX;
import static tars.commons.core.Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.task.Task;
import tars.model.task.rsv.RsvTask;

/**
 * Logic command test for confirm
 * 
 * @@author A0124333U
 */
public class ConfirmLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_confirm_invalidArgsFormatErrorMessageShown()
            throws Exception {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ConfirmCommand.MESSAGE_USAGE);
        assertCommandBehavior("confirm ", expectedMessage);
        assertCommandBehavior("confirm /p h 1 2", expectedMessage);
        assertCommandBehavior("confirm 1 1 -dt invalidFlag", expectedMessage);
        assertCommandBehavior("confirm 1 1 3", expectedMessage);
    }

    @Test
    public void execute_confirm_invalidRsvTaskIndexErrorMessageShown()
            throws Exception {
        assertCommandBehavior("confirm 2 3",
                MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_confirm_success() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();

        // Create added task
        Task addedTask = helper.generateTaskWithName("Test Task");

        // Create end state taskList with one confirmed task
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(addedTask);

        // Create Empty end state rsvTaskList
        List<RsvTask> rsvTaskList = new ArrayList<RsvTask>();

        RsvTask rsvTask =
                helper.generateReservedTaskWithOneDateTimeOnly("Test Task");

        Tars expectedTars = new Tars();
        expectedTars.addTask(addedTask);

        // Set Tars start state to 1 reserved task, and 0 tasks.
        model.resetData(new Tars());
        model.addRsvTask(rsvTask);

        String expectedMessage = String
                .format(ConfirmCommand.MESSAGE_CONFIRM_SUCCESS, addedTask);
        assertCommandBehaviorWithRsvTaskList("confirm 1 1 /p h /t tag",
                expectedMessage, expectedTars, taskList, rsvTaskList);

    }

    //@@author A0139924W
    @Test
    public void execute_undoAndRedo_confirmSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Task taskToConfirm = helper.generateTaskWithName("Reserved Task");
        Tars expectedTars = new Tars();
        expectedTars.addTask(taskToConfirm);

        // setup model
        model.addRsvTask(taskToRsv);

        String inputCommand = "confirm 1 1 /p h /t tag";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(ConfirmCommand.MESSAGE_CONFIRM_SUCCESS,
                        taskToConfirm),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeTask(taskToConfirm);
        expectedTars.addRsvTask(taskToRsv);

        // execute undo command and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());

        expectedTars.addTask(taskToConfirm);
        expectedTars.removeRsvTask(taskToRsv);

        // execute redo command and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());

    }

    @Test
    public void execute_undoAndRedo_confirmUnsuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Task taskToConfirm = helper.generateTaskWithName("Reserved Task");
        Tars expectedTars = new Tars();
        expectedTars.addTask(taskToConfirm);

        // setup model
        model.addRsvTask(taskToRsv);

        String inputCommand = "confirm 1 1 /p h /t tag";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(ConfirmCommand.MESSAGE_CONFIRM_SUCCESS,
                        taskToConfirm),
                expectedTars, expectedTars.getTaskList());

        model.addRsvTask(taskToRsv);
        expectedTars.addRsvTask(taskToRsv);
        
        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_DUPLICATE_TASK),
                expectedTars, expectedTars.getTaskList());

        model.deleteRsvTask(taskToRsv);
        expectedTars.removeRsvTask(taskToRsv);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_RSV_TASK_CANNOT_BE_FOUND),
                expectedTars, expectedTars.getTaskList());
    }
}
