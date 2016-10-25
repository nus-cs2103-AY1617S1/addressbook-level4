package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.logic.commands.taskcommands.RedoTaskCommand;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

//@@author A0143107U
public class RedoTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() {
		model = new TaskManager();
	}
	@Test
	public void redoTask_invalidState(){
		/* CommandResult should return a string that denotes invalid state
		 * if there is no previous undo command
		 */
		RedoTaskCommand command = new RedoTaskCommand();
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(RedoTaskCommand.MESSAGE_REDO_INVALID_STATE));
	}
	
	@Test
	public void undoTask_validState_add() throws DuplicateItemException {
		/* CommandResult should return a string that denotes success in execution if
		 * there is a previous undo command
		 */
		RedoTaskCommand command = new RedoTaskCommand();
		model.addTask(new FloatingTask("Meeting"));
		model.undo();
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(RedoTaskCommand.MESSAGE_REDO_TASK_SUCCESS));
	}
}
