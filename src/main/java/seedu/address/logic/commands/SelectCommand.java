package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.UnmodifiableObservableList;

/**
 * Selects a task identified using it's last displayed index
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    public static final String MESSAGE_SELECT_LAST_EMPTY_LIST = "No last task to select";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if(targetIndex == -1){ //Indicates a select last command
            int listSize = model.getFilteredTaskList().size();
            if(listSize < 1){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(MESSAGE_SELECT_LAST_EMPTY_LIST);
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(listSize));
            return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, listSize));
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }

}
