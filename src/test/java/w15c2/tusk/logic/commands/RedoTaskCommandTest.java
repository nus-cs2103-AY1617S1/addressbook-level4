package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.commons.collections.UniqueItemCollection.DuplicateItemException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.RedoTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.model.ModelManager;
import w15c2.tusk.model.task.FloatingTask;
//@@author A0143107U
/**
 * Tests RedoTask Command 
 */
public class RedoTaskCommandTest {

	// Initialized to support the tests
	Model model;

	@Before
	public void setup() {
		model = new ModelManager();
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
