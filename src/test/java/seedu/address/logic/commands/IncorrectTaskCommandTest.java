package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;

//@@author A0143107U
public class IncorrectTaskCommandTest {
	@Test
	public void incorrectTask_validFeedback() {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddTaskCommand constructor is a string with size > 0
		 */
		IncorrectTaskCommand command = new IncorrectTaskCommand(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(Messages.MESSAGE_INVALID_COMMAND_FORMAT));
	}
}
