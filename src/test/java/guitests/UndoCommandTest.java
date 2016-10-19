package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.UpdateCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    @Test
    public void update() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // undo add command
        assertUndoSuccess(td.hoon.getAddCommand(), currentList);
        
        // undo delete command
        assertUndoSuccess("delete " + targetIndex, currentList);
        
        // undo update command
        assertUndoSuccess("update " + targetIndex + td.ida.getArgs(), currentList);
    }
    
    private void assertUndoSuccess(String command, TestTask... originalList) {
        commandBox.runCommand(command);
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(originalList));
    }   
}