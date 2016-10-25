package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.logic.commands.taskcommands.UndoTaskCommand;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;
import seedu.address.testutil.TestUtil;

//@@author A0143107U
public class UndoTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() {
		model = new TaskManager();
	}
	
	@Test
	public void undoTask_invalidState(){
		/* CommandResult should return a string that denotes invalid state
		 * if there is no previous command to undo
		 */
		UndoTaskCommand command = new UndoTaskCommand();
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(UndoTaskCommand.MESSAGE_UNDO_INVALID_STATE));
	}
	
	@Test
	public void undoTask_validState_add() throws DuplicateItemException {
		/* CommandResult should return a string that denotes success in execution if
		 * there is a previous add command to undo
		 */
		UndoTaskCommand command = new UndoTaskCommand();
		model.addTask(new FloatingTask("Meeting"));
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(UndoTaskCommand.MESSAGE_UNDO_TASK_SUCCESS));
	}
	
	@Test
	public void undoTask_validState_delete() throws ItemNotFoundException, IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if
		 * there is a previous delete command to undo
		 */
		UndoTaskCommand command = new UndoTaskCommand();
		model = TestUtil.setupFloatingTasks(3);
		model.deleteTask(model.getCurrentFilteredTasks().get(2));
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(UndoTaskCommand.MESSAGE_UNDO_TASK_SUCCESS));
	}
	
	@Test
	public void undoTask_undoAgain_invalidState() throws DuplicateItemException {
		/* CommandResult should return a string that denotes invalid state
		 * if there is no previous command to undo(undo can only be used once)
		 */
		UndoTaskCommand command = new UndoTaskCommand();
		model.addTask(new FloatingTask("Meeting"));
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(UndoTaskCommand.MESSAGE_UNDO_TASK_SUCCESS));
		result = command.execute();
		feedback = result.feedbackToUser;
		assertTrue(feedback.equals(UndoTaskCommand.MESSAGE_UNDO_INVALID_STATE));
	}
	

}
