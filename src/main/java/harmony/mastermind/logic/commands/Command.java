package harmony.mastermind.logic.commands;

import java.text.SimpleDateFormat;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.events.ui.ExecuteCommandEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.model.Model;
import harmony.mastermind.storage.Storage;
import harmony.mastermind.storage.StorageManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    
    // Social date parser
    // @@author A0138862W
    protected static final PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
    
    protected Model model;
    protected Storage storage;

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
