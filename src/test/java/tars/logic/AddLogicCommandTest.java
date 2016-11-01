package tars.logic;

import static tars.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_DATE;
import static tars.commons.core.Messages.MESSAGE_INVALID_END_DATE;
import static tars.commons.core.Messages.MESSAGE_TASK_CANNOT_BE_FOUND;

import org.junit.Test;

import tars.logic.commands.AddCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.tag.Tag;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Task;

/**
 * Logic command test for add
 * 
 * @@author A0139924W
 */
public class AddLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add /dt 22/04/2016 1400 to 23/04/2016 2200 /p h Valid Task Name",
                expectedMessage);
        assertCommandBehavior("add", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] /dt 05/09/2016 1400 to 06/09/2016 2200 /p m",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add name - hello world /dt 05/09/2016 1400 to 06/09/2016 2200 /p m",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Task Name /dt @@@notAValidDate@@@ -p m",
                MESSAGE_INVALID_DATE);
        assertCommandBehavior(
                "add Valid Task Name /dt 05/09/2016 1400 to 01/09/2016 2200 /p m",
                MESSAGE_INVALID_END_DATE);
        assertCommandBehavior(
                "add Valid Task Name /dt 05/09/2016 1400 to 06/09/2016 2200 /p medium",
                Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Task Name /dt 05/09/2016 1400 to 06/09/2016 2200 /p m /t invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n"),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_add_endDateSuccessful() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.generateTaskWithEndDateOnly("Jane");
        Tars expectedTars = new Tars();
        expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n"),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_add_floatingTaskSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.floatingTask();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n"),
                expectedTars, expectedTars.getTaskList());

    }

    @Test
    public void execute_add_emptyTaskNameInvalidFormat() throws Exception {
        assertCommandBehavior("add ",
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE)));
    }

    // @@author A0140022H
    @Test
    public void execute_add_recurring() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Task toBeAdded2 = helper.meetAdam();
        toBeAdded2.setDateTime(
                new DateTime("08/09/2016 1400", "08/09/2016 1500"));
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);
        expectedTars.addTask(toBeAdded2);

        // execute command and verify result
        String expectedMessage =
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n");
        expectedMessage +=
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded2 + "\n");
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded).concat(" /r 2 every week"),
                expectedMessage, expectedTars, expectedTars.getTaskList());

    }

    @Test
    public void execute_add_duplicateNotAllowed() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                MESSAGE_DUPLICATE_TASK, expectedTars,
                expectedTars.getTaskList());
    }

    // @@author A0139924W
    @Test
    public void execute_undoAndRedo_addSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // execute add command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n"),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeTask(toBeAdded);

        // execute undo command and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS,
                        String.format(AddCommand.MESSAGE_UNDO, toBeAdded)),
                expectedTars, expectedTars.getTaskList());

        expectedTars.addTask(toBeAdded);

        // execute redo command and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS,
                        String.format(AddCommand.MESSAGE_SUCCESS,
                                toBeAdded + "\n")),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_undoAndRedo_addUnsuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        // execute add command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded + "\n"),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeTask(toBeAdded);
        model.deleteTask(toBeAdded);

        // execute undo command and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_TASK_CANNOT_BE_FOUND),
                expectedTars, expectedTars.getTaskList());

        model.addTask(toBeAdded);
        expectedTars.addTask(toBeAdded);

        // execute redo command and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_DUPLICATE_TASK),
                expectedTars, expectedTars.getTaskList());
    }
}
