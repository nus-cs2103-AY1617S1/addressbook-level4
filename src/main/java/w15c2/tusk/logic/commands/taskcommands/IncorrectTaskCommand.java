package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.logic.commands.CommandResult;

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
