package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.menion.testutil.TestActivity;
import seedu.menion.testutil.TestUtil;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

//@@author A0139515A
public class UndoRedoCommandTest extends ActivityManagerGuiTest {

    @Test
    public void add() {
        //add one activity
        TestActivity[] originalList = td.getTypicalTask();
        TestActivity activityToAdd = td.task2;
        
        assertAddSuccess(activityToAdd, originalList);
        TestActivity[] currentList = TestUtil.addActivitiesToList(originalList, activityToAdd);

        //testing undo command for adding of task
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command 
        commandBox.runCommand("redo");
        System.out.println(currentList.length);
        System.out.println(activityListPanel.getTaskListView().getItems().size());
        assertTrue(activityListPanel.isTaskListMatching(currentList));
        assertResultMessage("Menion successfully redo your previous changes");
        
        
        //testing undo command for deleting of task
        TestActivity[] beforeDeleteList = currentList;
       
        commandBox.runCommand("delete task 2");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isTaskListMatching(beforeDeleteList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command
        commandBox.runCommand("redo");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        assertResultMessage("Menion successfully redo your previous changes");
       
    
        //testing undo for clear command
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
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
        TaskCardHandle addedCard = activityListPanel.navigateToTask(activityToAdd.getActivityName().fullName);
        assertTaskMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous activities plus the new activity
        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isTaskListMatching(expectedList));
    }
}
