package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.util.List;

import org.junit.Test;

import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.logic.commands.EditCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Status;
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
        model.addTask(taskToAdd);
        Tars expectedTars = prepareExpectedTars();

        String inputCommand = "edit 1 /n Meet Betty Green /dt 20/09/2016 1800 "
                + "to 21/09/2016 1800 /p h /tr tag2 /ta tag3";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS,
                        expectedTars.getTaskList().get(0)),
                expectedTars, expectedTars.getTaskList());
    }
    
    @Test
    public void execute_edit_editedDuplicateTask() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task taskToAdd = helper.meetAdam();
        model.addTask(taskToAdd);
        Tars expectedTars = new Tars();
        expectedTars.addTask(taskToAdd);

        String inputCommand = "edit 1 /n Meet Adam Brown";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                new DuplicateTaskException().getMessage().toString(),
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
        Tars expectedTars = prepareExpectedTars();
        Task editedTask =
                expectedTars.getUniqueTaskList().getInternalList().get(0);

        model.addTask(taskToAdd);

        String inputCommand = "edit 1 /n Meet Betty Green /dt 20/09/2016 1800 "
                + "to 21/09/2016 1800 /p h /tr tag2 /ta tag3";

        // execute command and verify result
        assertCommandBehavior(inputCommand,
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS,
                        expectedTars.getTaskList().get(0)),
                expectedTars, expectedTars.getTaskList());
        
        expectedTars.replaceTask(editedTask, taskToAdd);

        // execute undo and verify result
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

    private static Tars prepareExpectedTars() throws IllegalValueException {
        Tars expectedTars = new Tars();
        Name editedName = new Name("Meet Betty Green");
        DateTime editedDateTime =
                new DateTime("20/09/2016 1800", "21/09/2016 1800");
        Priority editedPriority = new Priority("h");
        Status editedStatus = new Status(false);
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag3");
        UniqueTagList editedTags = new UniqueTagList(tag1, tag2);
        Task editedTask = new Task(editedName, editedDateTime, editedPriority,
                editedStatus, editedTags);
        expectedTars.addTask(editedTask);
        expectedTars.getUniqueTagList().add(new Tag("tag2"));

        return expectedTars;
    }
}
