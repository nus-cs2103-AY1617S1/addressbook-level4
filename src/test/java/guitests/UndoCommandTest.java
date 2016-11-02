package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.*;
import static org.junit.Assert.assertTrue;

//@@author A0125097A
public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undo_addCommand() {
        TestActivity[] currentList = td.getTypicalPersons();
        TestActivity activityToAdd = td.findHoon;
        assertUndoAddResult(activityToAdd,currentList);
        
    }
    
    @Test
    public void undo_deleteCommand() {
        TestActivity[] currentList = td.getTypicalPersons();
        int index = 2;
        assertUndoDeleteResult(index,currentList);
    }
    
    @Test
    public void undo_doneCommand() {
        TestActivity[] currentList = td.getTypicalPersons();
        int index = 4;
        assertUndoDoneResult(index,currentList);
    }

    @Test
    public void undo_editCommand() {
        TestActivity[] currentList = td.getTypicalPersons();
        int index = 1;
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
        assertResultMessage(String.format("Undo: Editting task from: %1$s\nto: %2$s",
                activityAfterUndo.getAsText(),activityBeforeUndo.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
  
        
    }

    private void assertUndoAddResult(TestActivity activity,TestActivity... currentList){
        commandBox.runCommand(activity.getAddCommand());
        commandBox.runCommand("undo");
        assertResultMessage(String.format("Undo: Adding of new task: %1$s",activity.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
    private void assertUndoDeleteResult(int index,TestActivity... currentList){
        TestActivity activityToDelete = currentList[index-1];
        
        commandBox.runCommand("delete " + index);
        commandBox.runCommand("undo");
        assertResultMessage(String.format("Undo: Deleting task: %1$s",activityToDelete.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
    }
    
    private void assertUndoDoneResult(int index,TestActivity... currentList){
        TestActivity activityToDone = currentList[index-1];
        
        commandBox.runCommand("done " + index);
        commandBox.runCommand("undo");
        assertResultMessage(String.format("Undo: Marked task as Completed: %1$s",activityToDone.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
}
