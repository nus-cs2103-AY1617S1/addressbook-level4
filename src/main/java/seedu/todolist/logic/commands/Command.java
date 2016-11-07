package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.EventsCenter;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.todolist.model.Model;
import seedu.todolist.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;

    /**
     * Constructs a feedback message to summarize an operation that displayed a listing of tasks.
     *
     * @param incompleteTaskSize, completedTaskSize and overdueTaskSize used to generate summary
     * @return summary message for incomplete, completed and overdue tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int incompleteTaskSize, int completedTaskSize, int overdueTaskSize) {
        return String.format(Messages.MESSAGE_INCOMPLETE_TASKS_LISTED_OVERVIEW, incompleteTaskSize)
        		+ String.format(Messages.MESSAGE_COMPLETED_TASKS_LISTED_OVERVIEW, completedTaskSize)
        		+ String.format(Messages.MESSAGE_OVERDUE_TASKS_LISTED_OVERVIEW, overdueTaskSize);
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
    
    //@@author A0158963M
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent());
    }
}
