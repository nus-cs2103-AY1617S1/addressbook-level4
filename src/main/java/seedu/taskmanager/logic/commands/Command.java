package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.taskmanager.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    
    public static final String MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME = "Start datetime must come before end datetime";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the task manager";
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, displaySize);
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
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
}
