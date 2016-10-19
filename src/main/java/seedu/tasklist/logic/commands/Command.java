package seedu.tasklist.logic.commands;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.tasklist.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

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
     * @throws IOException 
     * @throws JSONException 
     * @throws ParseException 
     */
    public abstract CommandResult execute() throws IOException, JSONException, ParseException;

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
