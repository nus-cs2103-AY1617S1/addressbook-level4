package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.CommandHistory;
import seedu.taskscheduler.logic.commands.UndoCommand;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.testutil.TestUtil;

public class UndoCommandTest extends TaskSchedulerGuiTest {
	
	@Test
    public void undo() {

		//clear mutate command history
		CommandHistory.flushMutateCmd();
        TestTask[] currentList = td.getTypicalTasks();
        ReadOnlyTask task = td.hoon;
        String commandKey;
        
        //undo add command
        commandKey = "add";
        commandBox.runCommand(td.hoon.getAddCommand());
        assertUndoSuccess(commandKey,currentList,task);
        
        //undo delete command
        commandKey = "delete";
        task = taskListPanel.getTask(0);
        commandBox.runCommand("delete 1");
        assertUndoSuccess(commandKey,currentList,task);

        //undo edit command
        commandKey = "edit";
        task = taskListPanel.getTask(1);
        commandBox.runCommand("edit " + 2 + " " + td.ida.getTaskString());
        assertUndoSuccess(commandKey,currentList,task);

        //undo mark command
        commandKey = "mark";
        task = taskListPanel.getTask(4);
        commandBox.runCommand("mark 5");
        assertUndoSuccess(commandKey,currentList,task);
        
        //undo multiple mixed commands
        commandBox.runCommand("edit " + 2 + " " + td.ida.getTaskString());
        commandBox.runCommand("mark 5");
        commandBox.runCommand("mark 3");
        commandBox.runCommand(td.hoon.getAddCommand());
        commandBox.runCommand("delete 3");
        for (int i = 0; i < 5; i++) {
        	commandBox.runCommand("undo");
        }
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //undo without any remaining previous command
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_FAILURE);
		
		//undo multiple delete commands
        commandBox.runCommand("delete 5");
        commandBox.runCommand("delete 5");
        commandBox.runCommand("delete 5");
        commandBox.runCommand("delete 5");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        for (int i = 0; i < 3; i++) {
            commandBox.runCommand("undo");
        }
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoSuccess(String commandKey, TestTask[] currentList, ReadOnlyTask... taskList) {
       
    	commandBox.runCommand("undo");

        //confirm the list now contains all previous tasks with the undo task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        assertTrue(taskListPanel.isListMatching(expectedList));
        StringBuilder sb = new StringBuilder();
        for (ReadOnlyTask testTask : taskList) {
            sb.append("\n");
            sb.append(testTask.getAsText());
        }
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS, commandKey, sb));
    }
}
