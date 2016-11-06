package guitests;

import static org.junit.Assert.assertTrue;
import static jym.manager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import jym.manager.testutil.TestTask;
import jym.manager.testutil.TestTaskList;
import jym.manager.commons.core.Messages;
import jym.manager.logic.commands.UndoCommand;

//@@author a0153617e
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        //without any last operation
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
    	
        //run add and edit operations
        TestTask taskToAdd = td.deadline;   
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask taskAfterEdit = td.event;
        commandBox.runCommand(taskAfterEdit.getEditCommand(1));
        
        //undo an edit operation
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        currentList.addTasksToList(taskToAdd);
        assertUndoCommandSuccess(currentList);
              
        //undo an add operation after undoing an edit operation
        currentList = new TestTaskList(td.getTypicalTasks());
        assertUndoCommandSuccess(currentList);
        
        //run done, delete, invalid and clear operations
        commandBox.runCommand("done 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete " + (currentList.getIncompleteList().length+2));
        commandBox.runCommand("clear");	
              
        //undo a clear operation        
        assertUndoCommandSuccess(currentList);
        
        //undo a delete operation
        currentList = new TestTaskList(td.getTypicalTasks());
        assertUndoCommandSuccess(currentList);
        
        //undo a complete operation
        currentList = new TestTaskList(td.getTypicalTasks());
        assertUndoCommandSuccess(currentList);

        //invalid command word
        commandBox.runCommand("undo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command argument
        commandBox.runCommand("undo 2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
    
    private void assertUndoCommandSuccess(TestTaskList expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedList.getCompleteList()));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
