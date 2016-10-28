package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.task.testutil.TestUtil;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.UndoCommand;

//@@author A0153736B
public class UndoCommandTest extends ToDoListGuiTest {

    /**
     * 
     */
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
        currentList.markTasksFromList(new TestTask[] {td.eventWithoutParameters});
        currentList.removeTasksFromList(new TestTask[] {td.eventWithLocation}, true);
        assertUndoCommandSuccess(currentList);
        
        //undo a delete operation
        currentList = new TestTaskList(td.getTypicalTasks());
        currentList.markTasksFromList(new TestTask[] {td.eventWithoutParameters});
        assertUndoCommandSuccess(currentList);
        
        //undo a done operation
        currentList = new TestTaskList(td.getTypicalTasks());
        assertUndoCommandSuccess(currentList);

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
