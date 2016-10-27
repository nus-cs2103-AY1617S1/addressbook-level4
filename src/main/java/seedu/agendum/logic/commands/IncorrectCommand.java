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

    public static String getName() {
        return null;
    }

    public static String getFormat() {
        return null;
    }

    public static String getDescription() {
        return null;
    }

}

