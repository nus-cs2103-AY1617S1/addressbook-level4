package seedu.ggist.logic.commands;

import java.util.Stack;

import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.ggist.model.Model;
import seedu.ggist.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
  //@@author A0138420N
    protected Model model;
    public static Stack<String> listOfCommands = new Stack<String>();
    public static Stack<ReadOnlyTask> listOfTasks = new Stack<ReadOnlyTask>();
    public static Stack<String> redoListOfCommands = new Stack<String>();
    public static Stack<ReadOnlyTask> redoListOfTasks = new Stack<ReadOnlyTask>();
    public static Stack<String> editTaskField = new Stack<String>();
    public static Stack<String> editTaskValue = new Stack<String>();
    public static Stack<String> redoEditTaskField = new Stack<String>();
    public static Stack<String> redoEditTaskValue = new Stack<String>();
  //@@author
    

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of task.
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
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
}
