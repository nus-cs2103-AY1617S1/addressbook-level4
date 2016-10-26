package seedu.cmdo.logic.commands;

import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.cmdo.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    public Boolean isUndoable = false;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of done tasks only.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     * 
     * @author A0139661Y
     */
    public static String getMessageForDoneTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_DONE_TASKS_LISTED_OVERVIEW, displaySize);
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