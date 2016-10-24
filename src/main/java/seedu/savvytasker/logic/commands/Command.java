package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
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
     * Provides any model related dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setModel(Model model) { /* Intentionally does nothing */ }
    
    /**
     * Provides any logic related dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setLogic(Logic logic) { /* Intentionally does nothing */ }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    public abstract boolean canUndo();
    
    /**
     * Redo the command.
     * @return true if the operation completed successfully, false otherwise
     */
    public abstract boolean redo();
    
    /**
     * Undo the command
     * @return true if the operation completed successfully, false otherwise
     */
    public abstract boolean undo();

    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    public abstract boolean isUndo();

    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    public abstract boolean isRedo();
}
