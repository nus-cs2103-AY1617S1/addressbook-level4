package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.model.task.Description;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class UpdateTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	/**
	 * Format of update command: update INDEX task/description/date(update type) UPDATED_VALUE
	 * 
	 * Testing correct handling of invalid indices, invalid update types, invalid dates and invalid 
	 */
	@Test
	public void prepareCommand_invalidIndex() throws IllegalValueException {
		/*
		 * Testing correct handling of invalid indices
		 */
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_invalidUpdateType() throws IllegalValueException {
		/*
		 * Testing correct handling of invalid update types
		 */
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_invalidDate() throws IllegalValueException {
		/*
		 * Testing correct handling of invalid dates
		 */
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_invalidUpdatedValue() throws IllegalValueException {
		/*
		 * Testing correct handling of an invalid updated value
		 */
		model = TestUtil.setupMixedTasks(6);
	}
	
	
	/**
	 * Testing correct updating of tasks from Floating <--> Deadline, Deadline <--> Event, Floating <--> Event
	 */
	@Test
	public void prepareCommand_floatingToDeadline() throws IllegalValueException {
		// 
		// TODO: ADD A FLOATING TASK
		//
		model = TestUtil.setupMixedTasks(6);
		//UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 task overseas from oct 1 to oct 2");
		String expectedTask = "[Update Task][Task: [Floating Task][Description: overseas][Start date: 01.10.16][End date: 02.10.16]]";
		//String actualTask = command.getTaskDetails();
		//assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineToFloating() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_deadlineToEvent() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_eventToDeadline() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_floatingToEvent() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_eventToFloating() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	/**
	 * Testing correct updating of description of tasks (Floating, Deadline & Event)
	 */
	@Test
	public void prepareCommand_floatingTask_updateDescription() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_deadlineTask_updateDescription() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_eventTask_updateDescription() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	/**
	 * Testing correct updating of dates of tasks (Floating - should fail, Deadline & Event) 
	 */
	@Test
	public void prepareCommand_floatingTask_updateDate() throws IllegalValueException {
		/*
		 * Should fail
		 */
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_deadlineTask_updateDate() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void prepareCommand_eventTask_updateDate() throws IllegalValueException {
		model = TestUtil.setupMixedTasks(6);
	}
}
