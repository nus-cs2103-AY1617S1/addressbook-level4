package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

//@@author A0139817U
public class AddTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() {
		model = new TaskManager();
	}
	
	@Test
	public void addTask_validDescription() throws IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddTaskCommand constructor is a string with size > 0
		 */
		AddTaskCommand command = new AddTaskCommand("Meeting");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "[Floating Task][Description: Meeting]")));

		Date deadline = new GregorianCalendar(2016, Calendar.OCTOBER, 31).getTime();
		command = new AddTaskCommand("Meeting", deadline);
		command.setData(model);
		result = command.execute();
		feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "[Deadline Task][Description: Meeting][Deadline: 31.10.2016]")));
		
		Date startDate = new GregorianCalendar(2016, Calendar.OCTOBER, 30).getTime();
		Date endDate = new GregorianCalendar(2016, Calendar.OCTOBER, 31).getTime();
		command = new AddTaskCommand("Meeting", startDate, endDate);
		command.setData(model);
		result = command.execute();
		feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "[Event Task][Description: Meeting][Start date: 30.10.2016][End date: 31.10.2016]")));
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_emptyStringDescription() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("");
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDescription() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		new AddTaskCommand(null);
	}

}
