package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.Task;
import seedu.address.testutil.TestUtil;

//@@author A0139817U
public class UpdateTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() throws IllegalValueException {
		// Creates fresh copy of tasks
		model = TestUtil.setupMixedTasks(6);
	}

	/**
	 * Format of update command: update INDEX task/description/date(update type) UPDATED_VALUE
	 * 
	 * Testing correct handling of invalid indices 
	 */
	@Test
	public void execute_invalidIndex() throws IllegalValueException {
		/*
		 * Testing correct handling of invalid indices
		 */
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		Description description = new Description("Testing");
		
		// There are only 6 tasks in the list, 7 exceeds the limit
		CommandResult result = createAndExecuteUpdateDescription(7, description);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		// The 1st task starts at 1, not 0
		result = createAndExecuteUpdateDescription(0, description);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing correct updating of tasks
	 */
	@Test
	public void execute_updateTask() throws IllegalValueException {
		Date firstDate = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime();
		Date secondDate = new GregorianCalendar(2016, Calendar.JANUARY, 2).getTime();		
				
		Task task = new FloatingTask("Hello");
		CommandResult result = createAndExecuteUpdateTask(2, task);
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, task);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		task = new DeadlineTask("Hello", firstDate);
		result = createAndExecuteUpdateTask(3, task);
		expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, task);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		task = new EventTask("Hello", firstDate, secondDate);
		result = createAndExecuteUpdateTask(4, task);
		expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, task);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing correct updating of description of tasks (Floating, Deadline & Event)
	 */
	@Test
	public void execute_floatingTask_updateDescription() throws IllegalValueException {
		Description description = new Description("Hello");
		
		// Index 1 is a floating task
		CommandResult result = createAndExecuteUpdateDescription(1, description);
		String updatedTask = "[Floating Task][Description: Hello]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_deadlineTask_updateDescription() throws IllegalValueException {
		Description description = new Description("Hello");
		
		// Index 2 is a deadline task
		CommandResult result = createAndExecuteUpdateDescription(2, description);
		String updatedTask = "[Deadline Task][Description: Hello][Deadline: 01.01.2016]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_eventTask_updateDescription() throws IllegalValueException {
		Description description = new Description("Hello");
		
		// Index 3 is an event task
		CommandResult result = createAndExecuteUpdateDescription(3, description);
		String updatedTask = "[Event Task][Description: Hello][Start date: 01.01.2016][End date: 02.01.2016]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing correct updating of dates of tasks (Floating, Deadline & Event) 
	 * 
	 * Note: Floating tasks can be converted to Deadline/Event tasks if updated with a date.
	 * 		Deadline tasks can be converted to Event tasks and vice versa.
	 * 		Deadline/Event tasks cannot be converted back to Floating tasks with update date.
	 */
	@Test
	public void execute_floatingTask_updateDate() throws IllegalValueException {
		Date firstDate = new GregorianCalendar(2016, Calendar.AUGUST, 20).getTime();
		Date secondDate = new GregorianCalendar(2016, Calendar.AUGUST, 21).getTime();
		/*
		 * Adding a deadline to the floating task by changing it to a deadline task.
		 */
		// Index 1 is a floating task
		CommandResult result = createAndExecuteUpdateDeadline(1, firstDate);
		String updatedTask = "[Deadline Task][Description: Task 0][Deadline: 20.08.2016]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		/*
		 * Adding a start date and end date to the floating task by changing it to an event task.
		 */
		result = createAndExecuteUpdateStartEndDate(1, firstDate, secondDate);
		updatedTask = "[Event Task][Description: Task 0][Start date: 20.08.2016][End date: 21.08.2016]";
		expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
	}
	
	@Test
	public void execute_deadlineTask_updateDate() throws IllegalValueException {
		Date firstDate = new GregorianCalendar(2016, Calendar.AUGUST, 20).getTime();
		Date secondDate = new GregorianCalendar(2016, Calendar.AUGUST, 21).getTime();
		
		/*
		 * Updating the deadline of a deadline task
		 */
		// Index 2 is a deadline task
		CommandResult result = createAndExecuteUpdateDeadline(2, firstDate);
		String updatedTask = "[Deadline Task][Description: Task 1][Deadline: 20.08.2016]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		/*
		 * Giving a start and end date to the deadline task by changing it to an event task.
		 */
		result = createAndExecuteUpdateStartEndDate(2, firstDate, secondDate);
		updatedTask = "[Event Task][Description: Task 1][Start date: 20.08.2016][End date: 21.08.2016]";
		expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_eventTask_updateDate() throws IllegalValueException {
		Date firstDate = new GregorianCalendar(2016, Calendar.AUGUST, 20).getTime();
		Date secondDate = new GregorianCalendar(2016, Calendar.AUGUST, 21).getTime();
		
		/*
		 * Updating the start and end date of a event task
		 */
		// Index 3 is an event task
		CommandResult result = createAndExecuteUpdateDeadline(3, firstDate);
		String updatedTask = "[Deadline Task][Description: Task 2][Deadline: 20.08.2016]";
		String expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
		
		/*
		 * Updating the start and end date of a event task
		 */
		result = createAndExecuteUpdateStartEndDate(3, firstDate, secondDate);
		updatedTask = "[Event Task][Description: Task 2][Start date: 20.08.2016][End date: 21.08.2016]";
		expected = String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask);
		feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Utility functions
	 */
	// Create and execute UpdateTaskCommand that updates the entire task of 'index' Task 
	public CommandResult createAndExecuteUpdateTask(int index, Task task) {
		UpdateTaskCommand command = new UpdateTaskCommand(index, task);
		command.setData(model);
		return command.execute();
	}
	
	// Create and execute UpdateTaskCommand that updates the description of 'index' Task
	public CommandResult createAndExecuteUpdateDescription(int index, Description description) {
		UpdateTaskCommand command = new UpdateTaskCommand(index, description);
		command.setData(model);
		return command.execute();
	}
	
	// Create and execute UpdateTaskCommand that updates the deadline of 'index' Task
	public CommandResult createAndExecuteUpdateDeadline(int index, Date deadline) {
		UpdateTaskCommand command = new UpdateTaskCommand(index, deadline);
		command.setData(model);
		return command.execute();
	}
	
	// Create and execute UpdateTaskCommand that updates the start date and the end date of 'index' Task
	public CommandResult createAndExecuteUpdateStartEndDate(int index, Date startDate, Date endDate) {
		UpdateTaskCommand command = new UpdateTaskCommand(index, startDate, endDate);
		command.setData(model);
		return command.execute();
	}
}
