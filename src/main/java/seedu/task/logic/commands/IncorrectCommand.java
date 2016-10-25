package seedu.task.logic.commands;

import seedu.task.model.task.Task;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

	@Override
	public CommandResult executeUndo() {
		return null;
	}


	@Override
	public boolean isReversible() {
		return false;
	}

}

