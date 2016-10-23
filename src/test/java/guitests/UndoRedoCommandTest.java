package guitests;

import guitests.guihandles.ActivityCardHandle;
import org.junit.Test;

import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;
import static org.junit.Assert.assertTrue;

public class UndoRedoCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one activity
        TestActivity[] originalList = td.getTypicalActivity();
        TestActivity activityToAdd = td.task;
        
        assertAddSuccess(activityToAdd, originalList);
        TestActivity[] currentList = TestUtil.addPersonsToList(originalList, activityToAdd);

        //testing undo command for adding of task
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isListMatching(originalList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command 
        commandBox.runCommand("redo");
        assertTrue(activityListPanel.isListMatching(currentList));
        assertResultMessage("Menion successfully redo your previous changes");
        
        
        //testing undo command for deleting of task
        TestActivity[] beforeDeleteList = currentList;
       
        commandBox.runCommand("delete task 2");
        assertTrue(activityListPanel.isListMatching(originalList));
        
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isListMatching(beforeDeleteList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command
        commandBox.runCommand("redo");
        assertTrue(activityListPanel.isListMatching(originalList));
        assertResultMessage("Menion successfully redo your previous changes");
       
    
        //testing undo for clear command
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isListMatching(originalList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command
        commandBox.runCommand("redo");
        assertListSize(0);
        assertResultMessage("Menion successfully redo your previous changes");

        //invalid command
        //there is 3 states to undo, undo 4 times to check message
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertResultMessage("Menion is unable to undo to your previous changes");
        
        //there is 3 states to redo, redo 4 times to check message
        commandBox.runCommand("redo");
        commandBox.runCommand("redo");
        commandBox.runCommand("redo");
        commandBox.runCommand("redo");
        assertResultMessage("Menion is unable to redo to your previous changes");
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        
        commandBox.runCommand(activityToAdd.getAddCommand());

        //confirm the new card contains the right data
        ActivityCardHandle addedCard = activityListPanel.navigateToActivity(activityToAdd.getActivityName().fullName);
        assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous activities plus the new activity
        TestActivity[] expectedList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(expectedList));
    }
}
