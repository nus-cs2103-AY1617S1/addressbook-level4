package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.CommandUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.PersonListPanel;

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastDatedTaskList = model.getFilteredDatedTaskList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();

        if (!CommandUtil.isValidIndex(targetIndex, lastUndatedTaskList.size(), 
                lastDatedTaskList.size(), PersonListPanel.DATED_DISPLAY_INDEX_OFFSET)){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        if (targetIndex > PersonListPanel.DATED_DISPLAY_INDEX_OFFSET) {
        	EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, JumpToListRequestEvent.DATED_LIST));
        }
        else {
        	EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, JumpToListRequestEvent.UNDATED_LIST));
        }
        
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    @Override
    public boolean isMutating() {
        return false;
    }

}
