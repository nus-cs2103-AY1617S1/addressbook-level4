package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.UndoCommand;

public class UndoCommandTest extends AddressBookGuiTest {

    /**
     * 
     */
    @Test
    public void undo() {
        //without any last operation
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
    	
        //undo one operation
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.goGym;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete " + currentList.length);
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertUndoCommandSuccess(expectedList);
              
        //undo another operation again after undoing one operation
        assertUndoCommandSuccess(currentList);

        //invalid command
        commandBox.runCommand("undo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertUndoCommandSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
