package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.forgetmenot.logic.commands.Command;
import seedu.forgetmenot.testutil.TestTask;

//@@author A0139671X
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        
        //creates a list of tasks for testing
        TestTask[] currentList = td.getTypicalTasks();

        // undo add command
        TestTask taskToAdd = td.hide;
        assertUndoAddSuccess(taskToAdd, currentList);

        // undo delete command
        int targetIndexToDelete = 1;
        assertUndoDeleteSuccess(targetIndexToDelete, currentList);

        // undo clear command
        assertUndoClearSuccess(currentList);

        // undo edit command
        int targetIndexToEdit = 1;
        assertUndoEditNameSuccess(targetIndexToEdit, currentList);
    }

    private void assertUndoEditNameSuccess(int targetIndex, TestTask[] currentList) {
        commandBox.runCommand("edit " + targetIndex + " new name");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoAddSuccess(TestTask taskToAdd, TestTask[] currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        commandBox.runCommand("undo");
        // confirms the list is the same as previously before add
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoDeleteSuccess(int targetIndex, TestTask[] currentList) {
        commandBox.runCommand("delete " + targetIndex);
        commandBox.runCommand("undo");

        // confirms the list is the same as previously before the delete
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoClearSuccess(TestTask[] currentList) {
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");

        // confirms the list is the same as previously before clear
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
