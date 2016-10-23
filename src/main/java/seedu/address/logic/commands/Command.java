package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Stack<TaskBook> undoStack;
    protected Stack<TaskBook> redoStack;
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @param todoSize 
     * @param deadlineSize 
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize, int deadlineSize, int todoSize) {
            return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize, deadlineSize, todoSize);
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
    public void setData(Model model, Stack<TaskBook> undoStack, Stack<TaskBook> redoStack) {
        this.model = model;
        this.undoStack = undoStack;
        this.redoStack = redoStack;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    protected void addToUndoStack() {
        TaskBook toBeAdded = new TaskBook(model.getAddressBook());
        undoStack.push(toBeAdded);
    }
}
