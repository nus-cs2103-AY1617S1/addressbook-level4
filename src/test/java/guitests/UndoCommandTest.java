package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
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
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        TestTask taskToAdd = td.event;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 1");
        currentList.addTasksToList(taskToAdd);
        assertUndoCommandSuccess(currentList);
              
        //undo another operation again after undoing one operation
        TestTask[] taskToDelete  = new TestTask[]{taskToAdd};
        currentList.removeTasksFromList(taskToDelete, true);
        assertUndoCommandSuccess(currentList);

        //invalid command
        commandBox.runCommand("undo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertUndoCommandSuccess(TestTaskList expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
