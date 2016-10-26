package seedu.jimi.logic.commands;

import java.util.Optional;

import seedu.jimi.commons.core.EventsCenter;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.events.storage.StoragePathChangedEvent;
import seedu.jimi.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.jimi.model.Model;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    
    protected Model origModel;
    
    protected static final String INDEX_TASK_LIST_PREFIX = "t";
    protected static final String INDEX_EVENT_LIST_PREFIX = "e";
    
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
    
    // @@author A0140133B
    /** 
     * Checks if a given string is a command word of this command.
     * Critical commands like "Exit" and "Clear" should have the user type the full command word for it to be valid.
     * 
     * @return true if given string is a valid command word of this command.
     */
    public abstract boolean isValidCommandWord(String commandWord);
    // @@author
    
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
        origModel = model.clone();
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    /** Raises an event to indicate the storage has changed */
    protected void indicateStoragePathChanged(String oldPath, String newPath) {
        EventsCenter.getInstance().post(new StoragePathChangedEvent(oldPath, newPath));
    }
    
    // @@author A0140133B
    /** Determines the list type according to the index prefix. */
    protected Optional<UnmodifiableObservableList<ReadOnlyTask>> determineListFromIndexPrefix(String idx) {
        if (idx.trim().startsWith(INDEX_TASK_LIST_PREFIX)) {
            return Optional.of(model.getFilteredAgendaTaskList());
        } else if (idx.trim().startsWith(INDEX_EVENT_LIST_PREFIX)) {
            return Optional.of(model.getFilteredAgendaEventList());
        }
        return Optional.empty();
    }
    //@@author

    //@@author A0148040R
    public void undo() {
        model.resetData(origModel.getTaskBook());
    }
    //@@author

}
