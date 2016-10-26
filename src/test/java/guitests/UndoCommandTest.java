package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tars.logic.commands.AddCommand;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.UndoCommand;
import tars.testutil.TestTask;
import tars.testutil.TestUtil;

public class UndoCommandTest extends TarsGuiTest {
    
    //@@author A0139924W
    @Test
    public void undo_add_successful() {
        // setup
        TestTask taskToUndo = td.taskH;
        commandBox.runCommand(taskToUndo.getAddCommand());
        
        commandBox.runCommand("undo");
        TestTask[] expectedList = {td.taskG};
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS,
                String.format(AddCommand.MESSAGE_UNDO, taskToUndo)));
    }
    
    //@@author A0139924W
    @Test
    public void undo_delete_successful() {
        // setup
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToUndo = currentList[currentList.length - 1];
        commandBox.runCommand("del " + currentList.length);
        
        commandBox.runCommand("undo");
        currentList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS,
                String.format(DeleteCommand.MESSAGE_UNDO, taskToUndo)));
    }
}
