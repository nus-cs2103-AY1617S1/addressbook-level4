package seedu.agendum.logic.commands;


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
    public String getName() {
        return null;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

}

