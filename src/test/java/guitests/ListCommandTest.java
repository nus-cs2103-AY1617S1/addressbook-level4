package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tars.testutil.TestTask;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_DATETIME;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_DATETIME_DESCENDING;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_PRIORITY;
import static tars.logic.commands.ListCommand.MESSAGE_SUCCESS_PRIORITY_DESCENDING;
import static tars.logic.commands.ListCommand.MESSAGE_USAGE;

/**
 * GUI test for list commands
 * 
 * @@author A0140022H
 */
public class ListCommandTest extends TarsGuiTest{
	
	TestTask[] currentList = td.getTypicalTasks();;
	
	@Test
	public void listAllTask() {
		TestTask[] expectedList = currentList;
		commandBox.runCommand("ls");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS);
	}
	
	@Test
	public void listAllTaskByDateTime() {
		TestTask[] expectedList = currentList;
		commandBox.runCommand("ls /dt");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_DATETIME);
	}
	
	@Test
	public void listAllTaskByDateTimeDescending() {
		TestTask[] expectedList = {td.taskG, td.taskF, td.taskE, td.taskD, td.taskC, td.taskB, td.taskA};
		commandBox.runCommand("ls /dt dsc");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_DATETIME_DESCENDING);
	}
	
	@Test
	public void listAllTaskByPriority() {
		TestTask[] expectedList = {td.taskC, td.taskF, td.taskB, td.taskE, td.taskA, td.taskD, td.taskG};
		commandBox.runCommand("ls /p");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_PRIORITY);
	}
	
	@Test
	public void listAllTaskByPriorityDescending() {
		TestTask[] expectedList = {td.taskA, td.taskD, td.taskG, td.taskB, td.taskE, td.taskC, td.taskF};
		commandBox.runCommand("ls /p dsc");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(MESSAGE_SUCCESS_PRIORITY_DESCENDING);
	}
	
	@Test
	public void listInvalidCommand() {
		//TestTask[] expectedList = {td.taskA, td.taskB, td.taskC, td.taskD, td.taskE, td.taskF};
		TestTask[] expectedList = currentList;
		commandBox.runCommand("ls /r");
		assertTrue(taskListPanel.isListMatching(expectedList));
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
	}
}
