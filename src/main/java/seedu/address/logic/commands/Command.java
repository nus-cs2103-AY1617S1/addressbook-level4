package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    protected static Stack<PreviousCommand> PreviousCommandsStack = new Stack<PreviousCommand>();

    public static final String MESSAGE_INVALID_ACTIVITY_TYPE = "Activity parameters should not contain both:\n"
            + "1. start time [and end time]  or\n"
            + "2. [due date] and/or [priority]";
    
    public static final String MESSAGE_INVALID_EVENT = "Event must contain a start time";
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
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
  //@@author A0125097A
    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     * @return "float", "task" or "event"
     * @throws IllegalValueException if Activity type cannot be determined
     */
    protected String identifyActivityType (String duedate, String priority, String start, String end) 
            throws IllegalValueException {
        
        if( (!duedate.isEmpty() || !priority.isEmpty()) && (!start.isEmpty() || !end.isEmpty()) ) {
            throw new IllegalValueException(MESSAGE_INVALID_ACTIVITY_TYPE);
        } else if ( duedate.isEmpty() && priority.isEmpty() && start.isEmpty() && end.isEmpty()){
            return "float";
        }
        
        if(!duedate.isEmpty() || !priority.isEmpty()) {
            return "task";
        } else if (start.isEmpty()) {
            throw new IllegalValueException(MESSAGE_INVALID_EVENT);
        } else {
            return "event";
        }
        
    }
    
}
