package seedu.address.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.parser.AddCommandParser;

public class AddCommandParserTest {

	// Initialized to support the tests
	AddCommandParser parser = new AddCommandParser();
	
	/**
	 * Testing situations in which user intends to create Floating Tasks.
	 */
	@Test
	public void prepareCommand_floatingTask_successful() {
		/*
		 * Normal add Floating task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("meeting");
		String expectedTask = "[Floating Task][Description: meeting]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing situations in which user intends to create Deadline Tasks.
	 */
	@Test
	public void prepareCommand_deadlineTask_successful() {
		/*
		 * Normal add Deadline task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("homework by Oct 12");
		String expectedTask = "[Deadline Task][Description: homework][Deadline: ]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_unsuccessfulDueToIncorrectDate() {
		/*
		 * Add Deadline task became add floating task because of incorrect dates.
		 * Instead of being a deadline task, it became a floating task with the incorrect date
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("homework by Ot 12");
		String expectedTask = "[Floating Task][Description: homework by Ot 12]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by Octo 12");
		expectedTask = "[Floating Task][Description: homework by Octo 12]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by Oct 35");
		expectedTask = "[Floating Task][Description: homework by Oct 35]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing situations in which user intends to create Event Tasks.
	 */
	@Test
	public void prepareCommand_eventTask_successful() {
		/*
		 * Normal add Event task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 to Oct 13");
		String expectedTask = "[Event Task][Description: project][Start Date: ][End Date: ]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 - Oct 13");
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}

}
