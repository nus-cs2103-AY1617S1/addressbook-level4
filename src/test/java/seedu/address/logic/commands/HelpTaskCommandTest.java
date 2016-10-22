package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.HelpTaskCommand;

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
