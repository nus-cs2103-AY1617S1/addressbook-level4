package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.ExitCommand;
//@@author A0143107U
/**
 * Tests Exit Command 
 */
public class ExitCommandTest {
	@Test
	public void execute_exit(){
		/* CommandResult should return a string that denotes that 
         * app is exited.
         */
		ExitCommand command = new ExitCommand();
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT));
	}
}
