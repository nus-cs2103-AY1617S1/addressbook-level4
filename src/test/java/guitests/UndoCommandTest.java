package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.*;

import static org.junit.Assert.assertTrue;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit 1 Accompany dad to the doctor, from 2016-10-25 02:00 to 2016-10-26 17:00 by 2016-10-27 15:00 #gwsDad");
        commandBox.runCommand("undo");
        assertUndoSuccess(currentList);
        
        commandBox.runCommand("delete 7");
        commandBox.runCommand("undo");
        assertUndoSuccess(currentList);
        
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAIL);
        
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertUndoSuccess(currentList);
        
        commandBox.runCommand("undoed");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        commandBox.runCommand("done 5");
        commandBox.runCommand("undo");
        assertUndoSuccess(currentList);
        
        commandBox.runCommand("done 6");
        commandBox.runCommand("undone 6");
        commandBox.runCommand("undo");
        commandBox.runCommand("undone 6");
        assertUndoSuccess(currentList);
        
    }

    private void assertUndoSuccess(TestTask... currentList) {
        

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = currentList;
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}