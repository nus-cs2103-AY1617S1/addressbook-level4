package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;

public class UndoCommandTest extends TaskManagerGuiTest {
    @Test
    public void undo() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // undo without previous command
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_ACTION_TO_UNDO);
        
        // undo add command
        assertUndoSuccess(td.hoon.getAddCommand(), currentList);
        
        // undo delete command
        assertUndoSuccess("delete " + targetIndex, currentList);
        
        // undo update command
        assertUndoSuccess("update " + targetIndex + td.ida.getArgs(), currentList);
        
        // undo pin command
        assertUndoSuccess("pin " + targetIndex, currentList);
        
        // undo complete command
        assertUndoSuccess("complete " + targetIndex, currentList);
    }
    
    private void assertUndoSuccess(String command, TestTask... originalList) {
        commandBox.runCommand(command);
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(originalList));
    }   
}