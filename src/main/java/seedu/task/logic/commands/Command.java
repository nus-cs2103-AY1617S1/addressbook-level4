package seedu.task.logic.commands;

import seedu.task.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.task.commons.exceptions.UndoableException;
import seedu.task.logic.UndoableCommandHistory;
import seedu.task.model.Model;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.Messages;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
	protected Model model;
    protected UndoableCommandHistory commandList;
    protected String arguments;

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
     * Constructs a feedback message to summarise an operation that displayed a listing of events.
     *
     * @param displaySize used to generate summary
     * @return summary message for events displayed
     */
    public static String getMessageForEventListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, displaySize);
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
     * Provides the command history for current command executed.
     */
    public void setCommandHistory(UndoableCommandHistory commandList) {
        this.commandList = commandList;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
}
