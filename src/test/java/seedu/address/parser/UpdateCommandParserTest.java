package seedu.address.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.logic.parser.UpdateCommandParser;

public class UpdateCommandParserTest {
	// Initialized to support the tests
	UpdateCommandParser parser = new UpdateCommandParser();
	
	/**
	 * Format of update command: update INDEX task/description/date(update type) UPDATED_VALUE
	 * 
	 * Testing correct handling of invalid indices, invalid update types, invalid dates and invalid 
	 */
	@Test
	public void prepareCommand_invalidIndex() {
		/*
		 * Testing correct handling of invalid indices
		 */
		
	}
	
	@Test
	public void prepareCommand_invalidUpdateType() {
		/*
		 * Testing correct handling of invalid update types
		 */
	}
	
	@Test
	public void prepareCommand_invalidDate() {
		/*
		 * Testing correct handling of invalid dates
		 */
	}
	
	@Test
	public void prepareCommand_invalidUpdatedValue() {
		/*
		 * Testing correct handling of an invalid updated value
		 */
	}
	
	
	/**
	 * Testing correct updating of tasks from Floating <--> Deadline, Deadline <--> Event, Floating <--> Event
	 */
	@Test
	public void prepareCommand_floatingToDeadline() {
		// 
		// TODO: ADD A FLOATING TASK
		//
		UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 task overseas from oct 1 to oct 2");
		String expectedTask = "[Update Task][Task: [Floating Task][Description: overseas][Start date: 01.10.16][End date: 02.10.16]]";
		String actualTask = command.getTaskDetails();
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineToFloating() {
		
	}
	
	@Test
	public void prepareCommand_deadlineToEvent() {
		
	}
	
	@Test
	public void prepareCommand_eventToDeadline() {
		
	}
	
	@Test
	public void prepareCommand_floatingToEvent() {
		
	}
	
	@Test
	public void prepareCommand_eventToFloating() {
		
	}
	
	/**
	 * Testing correct updating of description of tasks (Floating, Deadline & Event)
	 */
	@Test
	public void prepareCommand_floatingTask_updateDescription() {
		
	}
	
	@Test
	public void prepareCommand_deadlineTask_updateDescription() {
		
	}
	
	@Test
	public void prepareCommand_eventTask_updateDescription() {
		
	}
	
	/**
	 * Testing correct updating of dates of tasks (Floating - should fail, Deadline & Event) 
	 */
	@Test
	public void prepareCommand_floatingTask_updateDate() {
		/*
		 * Should fail
		 */
		
	}
	
	@Test
	public void prepareCommand_deadlineTask_updateDate() {
		
	}
	
	@Test
	public void prepareCommand_eventTask_updateDate() {
		
	}
	
	
}