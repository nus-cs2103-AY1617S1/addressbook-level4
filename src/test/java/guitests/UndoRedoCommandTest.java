package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.forgetmenot.logic.commands.RedoCommand;
import seedu.forgetmenot.logic.commands.UndoCommand;
import seedu.forgetmenot.testutil.TestTask;

//@@author A0139671X
public class UndoRedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        
        //creates a list of tasks for testing
        TestTask[] currentList = td.getTypicalTasks();
        
        // nothing to undo
        assertNothingToUndo();
        
        // undo delete command
        int targetIndexToDelete = 1;
        assertUndoDeleteSuccess(targetIndexToDelete, currentList);

        // undo clear command
        assertUndoClearSuccess(currentList);

        // undo edit command
        int targetIndexToEdit = 1;
        assertUndoEditNameSuccess(targetIndexToEdit, currentList);

        // undo add command
        TestTask taskToAdd = td.hide;
        assertUndoAddSuccess(taskToAdd, currentList);
        
        // redo add command
        assertRedoAfterUndoAdd(taskToAdd);
        
        // nothing to redo
        assertNothingToRedo();
    }

    public void assertNothingToUndo() {
        commandBox.runCommand("undo");
        
        // confirms there is nothing to undo
        assertResultMessage(UndoCommand.MESSAGE_UNDO_INVALID);
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
    
    public void assertNothingToRedo() {
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_REDO_INVALID);
    }
    
    public void assertRedoAfterUndoAdd(TestTask taskToAdd) {
        commandBox.runCommand("redo");
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        
        // confirms the previously added and undone task is in the list
        assertMatching(taskToAdd, addedCard);
    }
}
