package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectTaskCommand extends TaskCommand {

	 public final String feedbackToUser;

	    public IncorrectTaskCommand(String feedbackToUser){
	        this.feedbackToUser = feedbackToUser;
	    }

	    @Override
	    public CommandResult execute() {
	        indicateAttemptToExecuteIncorrectCommand();
	        return new CommandResult(feedbackToUser);
	    }

}
