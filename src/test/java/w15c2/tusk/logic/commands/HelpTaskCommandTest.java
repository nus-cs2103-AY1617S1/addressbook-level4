package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.logic.commands.CommandResult;
//@@author A0143107U
/**
 * Tests Help Command 
 */
public class HelpTaskCommandTest {

	@Test
	public void listHelp(){
		/* CommandResult should return a string that denotes that 
         * help is being shown.
         */
		HelpCommand command = new HelpCommand();
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(HelpCommand.SHOWING_HELP_MESSAGE));
	}
}
