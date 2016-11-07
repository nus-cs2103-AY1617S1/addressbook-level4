package guitests;

import org.junit.Test;

import seedu.lifekeeper.logic.commands.Command;
import seedu.lifekeeper.testutil.*;
import seedu.lifekeeper.logic.commands.UndoCommand;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

//@@author A0125097A
public class UndoCommandTest extends AddressBookGuiTest {

    @Before
    public void empty_PreviousCommandStack(){
    Command.emptyCommandStack();
    }
    
    @Test
    public void undo_addCommand() {
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity activityToAdd = td.findHoon;
        assertUndoAddResult(activityToAdd,currentList);
        
    }
    
    @Test
    public void undo_deleteCommand() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 2;
        assertUndoDeleteResult(index,currentList);
    }
    


    @Test
    public void undo_editActivity() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
       
        assertUndoEditActivityResult(index,currentList);
    }
    
    @Test
    public void undo_doneCommand() {
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 4;
        assertUndoDoneResult(index,currentList);
    }
    
    @Test
    public void undo_noPreviousCommand() {  
        assertUndoNoPreviousCommand();
    }
    


	private void assertUndoEditActivityResult(int index, TestActivity[] currentList) {
    	 String newName = "new name";
         String newReminder = "29-12-2021 2200";
         TestActivity activityBeforeEdit = new TestActivity(currentList[index-1]);
         TestActivity activityAfterEdit = new TestActivity(currentList[index-1]);
         activityAfterEdit.setName(newName);
         activityAfterEdit.setReminder(newReminder);
         String editCommand = "edit " + index + " n/" + newName + " r/" + newReminder;
    	
    	assertUndoEditResult(editCommand,activityBeforeEdit,activityAfterEdit,currentList);
	}

	private void assertUndoEditResult(String command, TestActivity activityAfterUndo, TestActivity activityBeforeUndo, TestActivity[] currentList) {
        commandBox.runCommand(command);
        commandBox.runCommand("undo"); 
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_EDIT_SUCCESS,
                activityAfterUndo.getAsText(),activityBeforeUndo.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
  
        
    }

    private void assertUndoAddResult(TestActivity activity,TestActivity... currentList){
        commandBox.runCommand(activity.getAddCommand());
        commandBox.runCommand("undo");
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_ADD_SUCCESS,activity.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
    private void assertUndoDeleteResult(int index,TestActivity... currentList){
        TestActivity activityToDelete = currentList[index-1];
        
        commandBox.runCommand("delete " + index);
        commandBox.runCommand("undo");
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_DELETE_SUCCESS,activityToDelete.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
    }
    
    private void assertUndoDoneResult(int index,TestActivity... currentList){
        TestActivity activityToDone = currentList[index-1];
        
        commandBox.runCommand("done " + index);
        commandBox.runCommand("undo");
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_DONE_SUCCESS,activityToDone.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
    private void assertUndoNoPreviousCommand() {
        commandBox.runCommand("undo"); 
        assertResultMessage(UndoCommand.MESSAGE_END_OF_UNDO);
	}
    
}
