package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;

public class UndoAndRedoCommandTest extends AddressBookGuiTest {
    
    @Test
    public void undoAndRedo() {
        // TODO: test delete more than one at once, done more than one at once
        
        // add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        
        // undo the add
        assertUndoSuccess(currentList);
        
        // nothing to undo
        assertUndoSuccess(currentList);
        
        // redo the add
        TestTask[] withHoon = TestUtil.addFloatingTasksToList(currentList, taskToAdd);
        assertRedoSuccess(withHoon);
        
        // delete hoon
        assertDeleteSuccess(8, withHoon);
        
        // undo the delete
        assertUndoSuccess(withHoon);
        
        // delete hoon again
        assertRedoSuccess(currentList);
        
        // type some non undoable command like "list"
        commandBox.runCommand("list");
        
        // undo the delete again
        assertUndoSuccess(withHoon);
                
        // redo the delete
        assertRedoSuccess(currentList);
        
        // delete some random index at 4
        assertDeleteSuccess(4, currentList);
        
        // undo the delete
        assertUndoSuccess(currentList);
        
        
        
    }
/*
    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addFloatingTasksToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addFloatingTasksToList(currentList, personToAdd);

        //add duplicate person
        //commandBox.runCommand(td.hoon.getAddCommand());
        //assertResultMessage(AddCommand.MESSAGE_DUPLICATE_FLOATING_TASK);
        //assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        
        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
    }
*/
    
    private void assertUndoSuccess(TestTask... expectedList) {
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    private void assertRedoSuccess(TestTask... expectedList) {
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(expectedList));
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToFloatingTask(personToAdd.getName().name);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addFloatingTasksToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
    }
        

}
