package harmony.mastermind.logic.commands;

import java.text.SimpleDateFormat;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.events.ui.ExecuteCommandEvent;
import harmony.mastermind.commons.events.ui.HighlightLastActionedRowRequestEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.model.Model;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.storage.Storage;
import harmony.mastermind.storage.StorageManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    
    // Social date parser
    protected static final PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
    
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
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, Storage storage) {
        this.model = model;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    // @@author A0138862W
    /**
     * Raises an event to highlight the last action row in table
     * This event should be subscribed by UiManager to update the table
     * 
     * @param the row that contain the task that needs to be highlighted
     * 
     */
    protected void requestHighlightLastActionedRow(Task task){
        EventsCenter.getInstance().post(new HighlightLastActionedRowRequestEvent(task));
    }
    
}
