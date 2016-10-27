package guitests;

import org.junit.Test;

import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.testutil.TestItem;
import static org.junit.Assert.assertTrue;

//@@author A0065571A
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        // undo once
        TestItem[] currentList = td.getTypicalItems();
        assertUndoSuccess(1, currentList);

        // undo twice
        assertUndoSuccess(2, currentList);

        // unto thrice
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
