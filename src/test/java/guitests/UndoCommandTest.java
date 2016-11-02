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
        int index = 2;
        assertUndoDeleteResult(index,currentList);
    }
    
    @Test
    public void undo_doneCommand() {
        TestActivity[] currentList = td.getTypicalPersons();
        int index = 4;
        assertUndoDoneResult(index,currentList);
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
