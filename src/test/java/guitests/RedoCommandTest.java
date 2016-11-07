package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.RedoCommand;
import seedu.todolist.model.task.Status;

//@@author A0153736B
public class RedoCommandTest extends ToDoListGuiTest {
	
	@Test
    public void redo() {
        //without any last undo operation
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
    	
        //redo an undo operation
        TestTask taskToAdd = td.overdueDeadline;   
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        currentList.addTasksToList(taskToAdd);
        assertRedoCommandSuccess(currentList);
        
        //redo two undo operations consecutively
        TestTask taskAfterEdit = td.upcomingEvent;
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
        
        //invalid command word
        commandBox.runCommand("redo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command argument
        commandBox.runCommand("redo 2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
    
    private void assertRedoCommandSuccess(TestTaskList expectedList) {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(Status.Type.Incomplete, expectedList.getIncompleteList()));
        assertTrue(taskListPanel.isListMatching(Status.Type.Complete, expectedList.getCompleteList()));
        assertTrue(taskListPanel.isListMatching(Status.Type.Overdue, expectedList.getOverdueList()));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
}
