package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tars.logic.commands.EditCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.UndoCommand;
import tars.logic.parser.ArgumentTokenizer;
import tars.model.Tars;
import tars.model.tag.Tag;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Task;

/**
 * Logic command test for edit
 * 
 * @@author A0124333U
 */
public class EditLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_edit_invalidArgsFormatErrorMessageShown()
            throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE);

        assertInvalidInputBehaviorForEditCommand("edit ", expectedMessage);
        assertInvalidInputBehaviorForEditCommand(
                "edit 1 -invalidFlag invalidArg", expectedMessage);
    }

    @Test
    public void execute_edit_indexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit");
    }

    @Test
    public void execute_edit_invalidTaskData() throws Exception {
        assertInvalidInputBehaviorForEditCommand("edit 1 /n []\\[;]",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertInvalidInputBehaviorForEditCommand(
                "edit 1 /dt @@@notAValidDate@@@", MESSAGE_INVALID_DATE);
        assertInvalidInputBehaviorForEditCommand("edit 1 /p medium",
                Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertInvalidInputBehaviorForEditCommand(
                "edit 1 /n validName /dt invalidDate", MESSAGE_INVALID_DATE);
        assertInvalidInputBehaviorForEditCommand("edit 1 /tr $#$",
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_edit_editedCorrectTask() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task taskToAdd = helper.meetAdam();
        List<Task> listToEdit = new ArrayList<Task>();
        listToEdit.add(taskToAdd);
        Tars expectedTars = new Tars();
        expectedTars.addTask(taskToAdd);

        // edit task
        String args =
                " /n Meet Betty Green /dt 20/09/2016 1800 to 21/09/2016 1800 /p h /ta tag3 /tr tag2";

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix,
                priorityPrefix, dateTimePrefix, addTagPrefix, removeTagPrefix);
        argsTokenizer.tokenize(args);

        Task taskToEdit = taskToAdd;
        Task editedTask = expectedTars.editTask(taskToEdit, argsTokenizer);
        helper.addToModel(model, listToEdit);

        String inputCommand = "edit 1 /n Meet Betty Green /dt 20/09/2016 1800 "
                + "to 21/09/2016 1800 /p h /tr tag2 /ta tag3";
        // execute command
        assertCommandBehavior(inputCommand, String
                .format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedTars, expectedTars.getTaskList());
    }

    private void assertInvalidInputBehaviorForEditCommand(String inputCommand,
            String expectedMessage) throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set Tars state to 2 tasks
        model.resetData(new Tars());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(inputCommand, expectedMessage, model.getTars(),
                taskList);
    }

    // @@author A0139924W
    @Test
    public void execute_undoAndRedo_editSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task taskToAdd = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(taskToAdd);

        // edit task
        String args =
                " /n Meet Betty Green /dt 20/09/2016 1800 to 21/09/2016 1800 /p h /ta tag3 /tr tag2";

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix,
                priorityPrefix, dateTimePrefix, addTagPrefix, removeTagPrefix);
        argsTokenizer.tokenize(args);

        model.addTask(taskToAdd);
        Task editedTask = expectedTars.editTask(taskToAdd, argsTokenizer);

        String inputCommand = "edit 1 " + args;

        // execute command and verify result
        assertCommandBehavior(inputCommand, String
                .format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedTars, expectedTars.getTaskList());

        expectedTars.replaceTask(editedTask, taskToAdd);

        // exceute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS,
                        String.format(EditCommand.MESSAGE_UNDO, taskToAdd)),
                expectedTars, expectedTars.getTaskList());

        expectedTars.replaceTask(taskToAdd, editedTask);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS,
                        String.format(EditCommand.MESSAGE_REDO, taskToAdd)),
                expectedTars, expectedTars.getTaskList());
    }
}
