package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.EventsCenter;
import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.taskscheduler.model.Model;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task scheduler";

    public static final String MESSAGE_REVERT_COMMAND = "Revert %s command: %s";
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();
    

    //@@author A0148145E
    /**
     * Revert the previous executed command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult revert();
    //@@author
    
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
    
    /**
     * Gets the task from list or last modified task
     * @param targetIndex
     * @return task from list if index > 0 or last modified task
     * @throws TaskNotFoundException if task is not found
     */
    protected ReadOnlyTask getTaskFromIndexOrLastModified(int targetIndex) 
            throws TaskNotFoundException {
        ReadOnlyTask task;
        if (targetIndex == -1) {
            task = CommandHistory.getModTask();
        }
        else {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            try { 
                task = lastShownList.get(targetIndex - 1);
            } catch (IndexOutOfBoundsException iobe){ 
                throw new TaskNotFoundException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        } 
        return task;
    }
}
