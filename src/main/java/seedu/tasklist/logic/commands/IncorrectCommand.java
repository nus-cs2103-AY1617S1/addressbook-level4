package seedu.tasklist.logic.commands;


/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

    /**
     * Parses arguments in the context of the incorrect command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        return new IncorrectCommand(args);
    }

}

