package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.model.ModelManager;

//@@author A0139817U
/**
 * Tests AddTask Command 
 */
public class AddTaskCommandTest {

	// Initialized to support the tests
	Model model;

	@Before
	public void setup() {
		model = new ModelManager();
	}
	
	@Test
	public void addTask_validDescription() throws IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddTaskCommand constructor is a string with size > 0
		 */
		// Floating task
		AddTaskCommand command = new AddTaskCommand("Meeting");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "Meeting")));

		// Deadline task
		Date deadline = new GregorianCalendar(2016, Calendar.OCTOBER, 31).getTime();
		command = new AddTaskCommand("Meeting", deadline);
		command.setData(model);
		result = command.execute();
		feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "Meeting")));
		
		// Event task
		Date startDate = new GregorianCalendar(2016, Calendar.OCTOBER, 30).getTime();
		Date endDate = new GregorianCalendar(2016, Calendar.OCTOBER, 31).getTime();
		command = new AddTaskCommand("Meeting", startDate, endDate);
		command.setData(model);
		result = command.execute();
		feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "Meeting")));
	}
	
	/**
	 * Tests exceptions for constructing Floating tasks
	 */
	@Test(expected=IllegalValueException.class)
	public void addTask_emptyStringDescriptionForFloatingTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("");
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDescriptionForFloatingTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		new AddTaskCommand(null);
	}
	
	/**
	 * Tests exceptions for constructing Deadline tasks
	 */
	@Test(expected=IllegalValueException.class)
	public void addTask_emptyStringDescriptionForDeadlineTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("", Calendar.getInstance().getTime());
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDescriptionForDeadlineTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		new AddTaskCommand(null, Calendar.getInstance().getTime());
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDeadlineForDeadlineTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("Valid", null);
	}
	
	/**
	 * Tests exceptions for constructing Event tasks
	 */
	@Test(expected=IllegalValueException.class)
	public void addTask_emptyStringDescriptionForEventTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDescriptionForEventTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		new AddTaskCommand(null, Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullStartDateForEventTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		new AddTaskCommand("Valid", null, Calendar.getInstance().getTime());
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullEndDateForEventTask() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		new AddTaskCommand("Valid", Calendar.getInstance().getTime(), null);
	}

}
