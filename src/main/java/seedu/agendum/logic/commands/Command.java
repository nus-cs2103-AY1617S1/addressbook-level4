package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.agendum.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    
    /**
     * Return the name of this command.
     */
    public static String getName() {
        return null;
    }

    /**
     * Return the format of this command.
     */
    public static String getFormat() {
        return null;
    }
    /**
     * Return the description of this command.
     */
    public static String getDescription() {
        return null;
    }
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent());
    }
}
