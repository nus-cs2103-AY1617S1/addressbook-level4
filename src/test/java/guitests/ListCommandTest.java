package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tars.testutil.TestTask;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_DONE;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_ALL;
import static tars.logic.commands.ListCommand.MESSAGE_USAGE;

/**
 * GUI test for list commands
 * 
 * @@author A0140022H
 */
public class ListCommandTest extends TarsGuiTest{
	
	TestTask[] currentList = td.getTypicalTasks();;
	
	@Test
	public void listAllUndoneTask() {
		TestTask[] expectedList = {td.taskA, td.taskB, td.taskC, td.taskD, td.taskE, td.taskF};
		commandBox.runCommand("ls");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS);
	}
	
	@Test
	public void listAllDoneTask() {
		TestTask[] expectedList = {td.taskG};
		commandBox.runCommand("ls -do");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_DONE);
	}
	
	@Test
	public void listAllTask() {
		TestTask[] expectedList = currentList;
		commandBox.runCommand("ls -all");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_ALL);
	}
	
	@Test
	public void listInvalidCommand() {
		TestTask[] expectedList = {td.taskA, td.taskB, td.taskC, td.taskD, td.taskE, td.taskF};
		commandBox.runCommand("ls -r");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
	}
}
