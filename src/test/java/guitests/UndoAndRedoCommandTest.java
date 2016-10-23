package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;

public class UndoAndRedoCommandTest extends AddressBookGuiTest {
    
    @Test
    public void undoAndRedo() {
        // TODO: test delete more than one at once, done more than one at once
        // This test seems pretty long...
        
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
        
        // clear the list
        assertClearCommandSuccess();
        assertUndoSuccess(currentList);
        assertRedoSuccess();
        
        // add alice
        TestTask aliceTask = new TestTask(td.alice);
        assertAddSuccess(aliceTask);
        
        // primitive edit undo and redo testing until the assertEditSuccess is complete
        TestTask aliceTaskBackup = new TestTask(aliceTask);
        commandBox.runCommand("edit 1 Call Alice from 2pm repeat every day -high");
        //commandBox.runCommand("edit 1 Call Alice from 2pm to 3pm repeat every day -high");
        aliceTask.setName(new Name("Call Alice"));
        aliceTask.setStartDate(DateTime.convertStringToDate("2pm"));
        //aliceTask.setEndDate(DateTime.convertStringToEndDate("3pm", aliceTask.getStartDate().get()));
        try {
            aliceTask.setRecurrence(new RecurrenceRate("1", "day"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceTask.setPriority(Priority.HIGH);
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        assertUndoSuccess(aliceTaskBackup);
        assertRedoSuccess(aliceTask);        

        TestTask recurredAliceTask = new TestTask(aliceTask);
        recurredAliceTask.setStartDate(DateTime.convertStringToDate("tomorrow 2pm"));

        // primitive done undo and redo testing until the assertDoneSuccess is complete
        commandBox.runCommand("done 1");
        assertTrue(personListPanel.isListMatching(recurredAliceTask));
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        // automatically directs me back to undone view
        assertUndoSuccess();
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        // automatically directs me back to undone view
        assertRedoSuccess(recurredAliceTask);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(aliceTask));

        commandBox.runCommand("list");
        
        // test whether the redo resets properly
        assertClearCommandSuccess();
        assertAddSuccess(taskToAdd);
        
        
        // this shows that the redo has reset
        assertUndoSuccess();
        assertAddSuccess(aliceTaskBackup);
        assertRedoSuccess(aliceTaskBackup);

        
        
        
        
    }
    
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
        
    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager has been cleared!");
    }

}
