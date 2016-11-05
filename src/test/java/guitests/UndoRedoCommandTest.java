package guitests;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

//@@author A0146123R
/**
 * gui tests for undo and redo commands
 */
public class UndoRedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undoRedo_invalidCommand_fail() {
        assertUndoFailed();
        assertRedoFailed();
    }

    @Test
    public void undo_exceedMaxTimes_fail() {
        for (int i = 0; i < 11; i++) {
            commandBox.runCommand("add n/dummy");
        }
        for (int i = 0; i < 10; i++) { // Undo up to 10 times
            commandBox.runCommand("undo");
        }
        assertListSize(9);
        assertUndoFailed();
        for (int i = 0; i < 10; i++) {
            commandBox.runCommand("redo");
        }
        assertListSize(19);
        assertRedoFailed();
    }

    @Test
    public void undoRedo_add_success() {
        TestTask taskToAdd = td.project;
        TestTask[] currentList = TestUtil.addTasksToList(td.getTypicalTasks(), taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoRedoSuccess(currentList);
    }

    @Test
    public void undoRedo_clear_success() {
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertUndoRedoClearSuccess();
    }

    @Test
    public void undoRedo_delete_success() {
        TestTask[] currentList = TestUtil.removeTaskFromList(td.getTypicalTasks(), 2);
        commandBox.runCommand("delete 2");
        assertUndoRedoSuccess(currentList);
    }

    @Test
    public void undoRedo_done_success() {
        TestTask[] currentList = td.getTypicalTasks();
        currentList[0].markAsDone();
        commandBox.runCommand("done 1");
        assertUndoRedoSuccess(currentList);
    }

    @Test
    public void undoRedo_edit_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        Date newDate = new Deadline("05.11.2016");
        currentList[3].setDate(newDate);
        commandBox.runCommand("edit Read book d/05.11.2016");
        assertUndoRedoSuccess(currentList);
    }

    private void assertUndoFailed() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILED);
    }

    private void assertRedoFailed() {
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_REDO_FAILED);
    }

    /**
     * Runs the undo and redo command and confirms the result is correct.
     */
    private void assertUndoRedoSuccess(TestTask[] currentList) {
        commandBox.runCommand("undo");
        TestTask[] initialList = new TypicalTestTasks().getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(initialList));
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    /**
     * Runs the undo and redo command for clear command and confirms the result
     * is correct.
     */
    private void assertUndoRedoClearSuccess() {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("redo");
        assertListSize(0);
    }

}