package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.events.undo.ExecutedIncorrectCommandEvent;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    private static boolean isAddEditDeleteCommand;
    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        indicateExecutedIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

    public static void setIsUndoableCommand(boolean value) {
        isAddEditDeleteCommand = value;
    }
    
    public static boolean isUndoableCommand() {
        return isAddEditDeleteCommand;
    }
    
    private void indicateExecutedIncorrectCommand() {
        EventsCenter.getInstance().post(new ExecutedIncorrectCommandEvent(isAddEditDeleteCommand));
    }

}

