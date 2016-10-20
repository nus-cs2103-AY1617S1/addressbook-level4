package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertTrue;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 1;
        assertUndoSuccess(1, currentList);

        //add another person
        targetIndex = 2;
        assertUndoSuccess(2, currentList);

        //add another person
        targetIndex = 1;
        assertUndoSuccess(3, currentList);
    }
    
    @Test
    public void undoFailure() {
        // undo but can't
    	commandBox.runCommand("select 1");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_DONE_FAILURE);
    }

    private void assertUndoSuccess(int numTimes, TestItem... currentList) {
        for (int i=0; i<numTimes; i++) {
    	    commandBox.runCommand("clear");
        }
        for (int i=0; i<numTimes; i++) {
            commandBox.runCommand("undo");
        }
        assertTrue(shortItemListPanel.isListMatching(currentList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_DONE_SUCCESS, ClearCommand.MESSAGE_SUCCESS));
    }    
    
}
