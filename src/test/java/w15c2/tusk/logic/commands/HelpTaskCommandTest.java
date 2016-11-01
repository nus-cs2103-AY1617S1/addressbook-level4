package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.HelpTaskCommand;

//@@author A0143107U
public class HelpTaskCommandTest {

	@Test
	public void listHelp(){
		/* CommandResult should return a string that denotes that 
         * help is being shown.
         */
		HelpTaskCommand command = new HelpTaskCommand();
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(HelpTaskCommand.SHOWING_HELP_MESSAGE));
	}
}
