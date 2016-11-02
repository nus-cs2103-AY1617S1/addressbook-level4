package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TypicalTestActivities;
import static org.junit.Assert.assertTrue;

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
        int Index = 2;
        TestActivity activityToDelete = currentList[Index-1] ;
        
        commandBox.runCommand("delete " + Index);
        commandBox.runCommand("undo");
        assertResultMessage(String.format("Undo: Deleting task: %1$s",activityToDelete.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
         
    }
    
    
    private void assertUndoResult(String command,TestActivity... currentList){
        commandBox.runCommand(command);
        commandBox.runCommand("undo");
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
    private void assertUndoAddResult(TestActivity activity,TestActivity... currentList){
        commandBox.runCommand(activity.getAddCommand());
        commandBox.runCommand("undo");
        assertResultMessage(String.format("Undo: Adding of new task: %1$s",activity.getAsText()));
        assertTrue(activityListPanel.isListMatching(currentList));
        
    }
    
    
}
