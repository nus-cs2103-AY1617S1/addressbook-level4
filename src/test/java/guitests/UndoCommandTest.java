package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.ClearCommand;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.CommandHistory;
import seedu.taskscheduler.logic.commands.UndoCommand;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.testutil.TestUtil;

//@@author A0140007B

public class UndoCommandTest extends TaskSchedulerGuiTest {
	
	@Test
    public void undo() {

		//clear mutate command history
		CommandHistory.flushExecutedCommands();
        TestTask[] currentList = td.getTypicalTasks();
        ReadOnlyTask task = td.event;
        String commandKey;
        
        //undo add command
        commandKey = "add";
        commandBox.runCommand(td.event.getAddCommand());
        assertUndoSuccess(commandKey,currentList,task);
        
        //undo delete command
        commandKey = "delete";
        task = taskListPanel.getTask(0);
        commandBox.runCommand(commandKey + " 1");
        assertUndoSuccess(commandKey,currentList,task);

        //undo replace command
        commandKey = "replace";
        task = taskListPanel.getTask(1);
        commandBox.runCommand(commandKey + " 2 " + td.ida.getTaskString());
        assertUndoSuccess(commandKey,currentList,td.ida);

        //undo mark command
        commandKey = "mark";
        task = taskListPanel.getTask(4);
        commandBox.runCommand(commandKey + " 5");
        assertUndoSuccess(commandKey,currentList,task);
        
        //@@author A0138969L
        //undo unmark command
        commandBox.runCommand("mark 5");
        commandKey = "unmark";
        task = taskListPanel.getTask(4);
        commandBox.runCommand(commandKey + " 5");
        assertUndoSuccess(commandKey,currentList,task);
        //@
        
        //undo multiple mixed commands
        assertUndoMixedCommandsSuccess(currentList);
        
        //undo without any remaining previous command
		assertUndoFailure();
		
        assertUndoMultipleDeleteSuccess(currentList);

        assertUndoClearSuccess(currentList);
    }

    private void assertUndoMixedCommandsSuccess(TestTask[] currentList) {
        commandBox.runCommand("replace " + 2 + " " + td.ida.getTaskString());
        commandBox.runCommand("mark 5");
        commandBox.runCommand("mark 3");
        commandBox.runCommand(td.event.getAddCommand());
        commandBox.runCommand("delete 3");
        for (int i = 0; i < 5; i++) {
        	commandBox.runCommand(UndoCommand.COMMAND_WORD);
        }
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoFailure() {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
		assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }

    private void assertUndoMultipleDeleteSuccess(TestTask[] currentList) {
        for (int i = 0; i < 4; i++) {
            commandBox.runCommand("delete 5");
        }
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        for (int i = 0; i < 3; i++) {
            commandBox.runCommand(UndoCommand.COMMAND_WORD);
        }
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoClearSuccess(TestTask[] currentList) {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoSuccess(String commandKey, TestTask[] currentList, ReadOnlyTask... taskList) {
       
    	commandBox.runCommand(UndoCommand.COMMAND_WORD);

        //confirm the list now contains all previous tasks with the undo task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        assertTrue(taskListPanel.isListMatching(expectedList));
        StringBuilder sb = new StringBuilder();
        for (ReadOnlyTask testTask : taskList) {
            sb.append("\n");
            sb.append(testTask.getAsText());
        }
        assertResultMessage(String.format(Command.MESSAGE_REVERT_COMMAND, commandKey, sb));
    }
}
