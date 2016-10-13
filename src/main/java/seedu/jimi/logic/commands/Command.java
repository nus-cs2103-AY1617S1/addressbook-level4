package seedu.jimi.logic.commands;

import seedu.jimi.commons.core.EventsCenter;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.jimi.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

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
     * Checks if a given string is a command word of this command.
     * Critical commands like "Exit" and "Clear" should have the user type the full command word for it to be valid.
     * 
     * @return true if given string is a valid command word of this command.
     */
    public abstract boolean isValidCommandWord(String commandWord);
    
    /**
     * Returns command word corresponding to this command.
     */
    public abstract String getCommandWord();
    
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
