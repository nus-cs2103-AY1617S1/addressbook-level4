package tars.logic;

import static tars.commons.core.Messages.MESSAGE_CONFLICTING_TASKS_WARNING;
import static tars.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_DATE;
import static tars.commons.core.Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tars.logic.commands.RedoCommand;
import tars.logic.commands.RsvCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.task.DateTime;
import tars.model.task.Task;
import tars.model.task.rsv.RsvTask;

/**
 * Logic command test for rsv
 */
public class RsvLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_rsvInvalidArgsFormatErrorMessageShown()
            throws Exception {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RsvCommand.MESSAGE_USAGE);
        assertCommandBehavior("rsv ", expectedMessage);
    }

    @Test
    public void execute_rsvAddInvalidArgsFormatErrorMessageShown()
            throws Exception {
        String expectedMessageForNullDate =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RsvCommand.MESSAGE_DATETIME_NOT_FOUND);
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RsvCommand.MESSAGE_USAGE);
        assertCommandBehavior("rsv Rsv Task Without Date",
                expectedMessageForNullDate);
        assertCommandBehavior("rsv Rsv Task with flags other than date -p h",
                expectedMessageForNullDate);
        assertCommandBehavior("rsv /dt tomorrow", expectedMessage);
        assertCommandBehavior("rsv Rsv Task with invalid Date /dt invalidDate",
                MESSAGE_INVALID_DATE);
    }

    @Test
    public void execute_rsvDelInvalidArgsFormatErrorMessageShown()
            throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RsvCommand.MESSAGE_USAGE_DEL);
        assertCommandBehavior("rsv invalidArgs /del 1", expectedMessage);
        assertCommandBehavior("rsv /del invalidValue", expectedMessage);
    }

    @Test
    public void execute_rsv_del_successful() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();

        // Create a reserved task
        RsvTask rsvTask =
                helper.generateReservedTaskWithOneDateTimeOnly("Test Task");

        // Create empty taskList
        List<Task> taskList = new ArrayList<Task>();

        // Create empty end state rsvTaskList
        List<RsvTask> rsvTaskList = new ArrayList<RsvTask>();

        // Create empty end state Tars
        Tars expectedTars = new Tars();

        // Set Tars start state to 1 reserved task, and 0 tasks.
        model.resetData(new Tars());
        model.addRsvTask(rsvTask);

        String expectedMessage = String.format(RsvCommand.MESSAGE_SUCCESS_DEL,
                "1.\t" + rsvTask + "\n");
        assertCommandBehaviorWithRsvTaskList("rsv /del 1", expectedMessage,
                expectedTars, taskList, rsvTaskList);
    }

    @Test
    public void execute_rsv_conflictingTaskShowWarning() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask rsvTaskA =
                helper.generateReservedTaskWithNameAndDate("Rsv Task A",
                        new DateTime("14/10/2016 0900", "16/10/2016 0900"));
        RsvTask taskToRsv =
                helper.generateReservedTaskWithNameAndDate("Task To Rsv",
                        new DateTime("13/10/2016 1000", "15/10/2016 1000"));

        // Create empty taskList
        List<Task> taskList = new ArrayList<Task>();

        List<RsvTask> rsvTaskList = new ArrayList<RsvTask>();
        rsvTaskList.add(rsvTaskA);
        rsvTaskList.add(taskToRsv);

        Tars expectedTars = new Tars();
        String expectedMessage =
                String.format(RsvCommand.MESSAGE_SUCCESS, taskToRsv.toString())
                        + "\n" + MESSAGE_CONFLICTING_TASKS_WARNING
                        + "\nConflicts for "
                        + taskToRsv.getDateTimeList().get(0).toString() + ": "
                        + "\nRsvTask 1: " + rsvTaskA.toString();

        expectedTars.addRsvTask(rsvTaskA);
        expectedTars.addRsvTask(taskToRsv);

        model.resetData(new Tars());
        model.addRsvTask(rsvTaskA);

        assertCommandBehaviorWithRsvTaskList(
                "rsv Task To Rsv /dt 13/10/2016 1000 to 15/10/2016 1000",
                expectedMessage, expectedTars, taskList, rsvTaskList);

    }

    //@@author A0139924W
    @Test
    public void execute_undoAndRedo_rsvSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Tars expectedTars = new Tars();
        expectedTars.addRsvTask(taskToRsv);

        String inputCommand =
                "rsv Reserved Task /dt 05/09/2016 1400 to 06/09/2016 2200";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(RsvCommand.MESSAGE_SUCCESS, taskToRsv),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeRsvTask(taskToRsv);

        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS,
                        String.format(RsvCommand.MESSAGE_UNDO_DELETE,
                                taskToRsv)),
                expectedTars, expectedTars.getTaskList());

        expectedTars.addRsvTask(taskToRsv);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS,
                        String.format(RsvCommand.MESSAGE_REDO_ADD, taskToRsv)),
                expectedTars, expectedTars.getTaskList());

    }

    @Test
    public void execute_undoAndRedo_rsvUnsuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Tars expectedTars = new Tars();
        expectedTars.addRsvTask(taskToRsv);

        String inputCommand =
                "rsv Reserved Task /dt 05/09/2016 1400 to 06/09/2016 2200";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(RsvCommand.MESSAGE_SUCCESS, taskToRsv),
                expectedTars, expectedTars.getTaskList());

        model.deleteRsvTask(taskToRsv);
        expectedTars.removeRsvTask(taskToRsv);

        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_RSV_TASK_CANNOT_BE_FOUND),
                expectedTars, expectedTars.getTaskList());

        model.addRsvTask(taskToRsv);
        expectedTars.addRsvTask(taskToRsv);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_DUPLICATE_TASK),
                expectedTars, expectedTars.getTaskList());

    }

    @Test
    public void execute_undoAndRedo_rsvDelSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Tars expectedTars = new Tars();

        // setup model
        model.addRsvTask(taskToRsv);

        String inputCommand = "rsv /del 1";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(RsvCommand.MESSAGE_SUCCESS_DEL,
                        "1.\t" + taskToRsv + "\n"),
                expectedTars, expectedTars.getTaskList());

        expectedTars.addRsvTask(taskToRsv);

        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS,
                        String.format(RsvCommand.MESSAGE_UNDO_ADD,
                                "1.\t" + taskToRsv + "\n")),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeRsvTask(taskToRsv);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS,
                        String.format(RsvCommand.MESSAGE_REDO_DELETE,
                                "1.\t" + taskToRsv + "\n")),
                expectedTars, expectedTars.getTaskList());

    }

    @Test
    public void execute_undoAndRedo_rsvDelUnsuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        RsvTask taskToRsv =
                helper.generateReservedTaskWithOneDateTimeOnly("Reserved Task");
        Tars expectedTars = new Tars();

        // setup model
        model.addRsvTask(taskToRsv);

        String inputCommand = "rsv /del 1";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(RsvCommand.MESSAGE_SUCCESS_DEL,
                        "1.\t" + taskToRsv + "\n"),
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
