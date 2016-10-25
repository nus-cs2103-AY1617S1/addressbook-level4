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
    	
        //run add and edit operations
        TestTaskList expectedListBeforeAdd = new TestTaskList(td.getTypicalTasks());
        TestTask taskToAdd = td.deadline;   
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTaskList expectedListBeforeEdit = TestUtil.addTasksToList(expectedListBeforeAdd, taskToAdd);
        TestTask taskAfterEdit = td.event;
        commandBox.runCommand(taskAfterEdit.getEditCommand(1));
        
        //undo an edit operation
        assertUndoCommandSuccess(expectedListBeforeEdit);
              
        //undo an add operation after undoing an edit operation
        assertUndoCommandSuccess(expectedListBeforeAdd);
        
        //run done, delete, invalid and clear operations
        TestTaskList expectedListBeforeDone = expectedListBeforeAdd;
        commandBox.runCommand("done 1");
        TestTaskList expectedListBeforeDelete = TestUtil.markTaskFromList(expectedListBeforeDone, new int[]{1});
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete " + expectedListBeforeDelete.getIncompleteList().length + 1);
        TestTaskList expectedListBeforeClear = TestUtil.removeTaskFromList(expectedListBeforeDelete, new int[]{1});
        commandBox.runCommand("clear");	
              
        //undo a clear operation
        assertUndoCommandSuccess(expectedListBeforeClear);
        
        //undo a delete operation
        assertUndoCommandSuccess(expectedListBeforeDelete);
        
        //undo a done operation
        assertUndoCommandSuccess(expectedListBeforeDone);

        //invalid command
        commandBox.runCommand("undo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertUndoCommandSuccess(TestTaskList expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedList.getCompleteList()));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
