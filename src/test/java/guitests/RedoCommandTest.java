package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.RedoCommand;

//@@author A0153736B
public class RedoCommandTest extends ToDoListGuiTest {
	
	@Test
    public void redo() {
        //without any last undo operation
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
    	
        //redo an undo operation
        TestTask taskToAdd = td.deadline;   
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        currentList.addTasksToList(taskToAdd);
        assertRedoCommandSuccess(currentList);
        
        //redo two undo operations consecutively
        TestTask taskAfterEdit = td.event;
        commandBox.runCommand(taskAfterEdit.getEditCommand(1));
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertRedoCommandSuccess(currentList);
        currentList.editTask(1, taskAfterEdit, true);
        assertRedoCommandSuccess(currentList);
        
        //redo operation fails when apply delete operation after undo operation
        commandBox.runCommand("undo");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        
        //invalid command
        commandBox.runCommand("redo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertRedoCommandSuccess(TestTaskList expectedList) {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedList.getCompleteList()));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
}
