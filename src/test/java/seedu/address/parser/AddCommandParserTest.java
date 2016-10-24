package seedu.address.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.parser.AddCommandParser;

//@@author A0139817U
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
		String expectedTask = "[Deadline Task][Description: homework][Deadline: 12.10.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by 1 Jan 2016");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_multipleKeywords() {
		/*
		 * Multiple "from" and "by" keywords should not affect the task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("from by from by Oct 12");
		String expectedTask = "[Deadline Task][Description: from by from][Deadline: 12.10.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_looksLikeEventTask() {
		/*
		 * DeadlineTask that looks like EventTask due to the presence of "from" and "to" keywords.
		 * If "by" comes after the "from" key word, it is a DeadlineTask.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("Camp from May 11 to May 12 by May 1");
		String expectedTask = "[Deadline Task][Description: Camp from May 11 to May 12][Deadline: 01.05.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_unsuccessfulDueToIncorrectDate() {
		/*
		 * DeadlineTask becomes a FloatingTask because of incorrect date formats.
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
	
	@Test
	public void prepareCommand_deadlineTask_unsuccessfulDueToIncorrectFormat() {
		/*
		 * DeadlineTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("passerby Oct 31");
		String expectedTask = "[Floating Task][Description: passerby Oct 31]";
		String actualTask = command.getTaskDetails();
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
		String expectedTask = "[Event Task][Description: project][Start date: 12.10.2016][End date: 13.10.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 - Oct 13");
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from 12 October 2016 to 13 October 2016");
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_multipleKeywords() {
		/*
		 * Multiple "from" and "by" keywords should not affect the task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("from by from by from Oct 1 - Oct 2");
		String expectedTask = "[Event Task][Description: from by from by][Start date: 01.10.2016][End date: 02.10.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_looksLikeDeadlineTask() {
		/*
		 * EventTask that looks like DeadlineTask due to the presence of "by" keyword.
		 * If "from" comes after the "by" key word, it is an EventTask.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("Concert by 1 December from May 1 to May 2");
		String expectedTask = "[Event Task][Description: Concert by 1 December][Start date: 01.05.2016][End date: 02.05.2016]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_unsuccessfulDueToIncorrectDate() {
		/*
		 * EventTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("project from Octo 12 to Oct 13");
		String expectedTask = "[Floating Task][Description: project from Octo 12 to Oct 13]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 -- Oct 13");
		expectedTask = "[Floating Task][Description: project from Oct 12 -- Oct 13]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project fr Oct 12 to Oct 13");
		expectedTask = "[Floating Task][Description: project fr Oct 12 to Oct 13]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 t Oct 13");
		expectedTask = "[Floating Task][Description: project from Oct 12 t Oct 13]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project by Oct 12 to Oct 13");
		expectedTask = "[Floating Task][Description: project by Oct 12 to Oct 13]";
		actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}

	@Test
	public void prepareCommand_eventTask_unsuccessfulDueToIncorrectFormat() {
		/*
		 * EventTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("refrom Oct 30 to Oct 31");
		String expectedTask = "[Floating Task][Description: refrom Oct 30 to Oct 31]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
}
