//@@author A0139772U-reused
package seedu.whatnow.logic.commands;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to
 * the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    /**
     * Executes the IncorrectCommand to display the feedback message to user
     */
    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

}
