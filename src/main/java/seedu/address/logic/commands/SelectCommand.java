package seedu.address.logic.commands;

import java.util.ArrayList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.UnmodifiableObservableList;

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final ArrayList<Integer> targetIndexes;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(ArrayList<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() {      

        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        for(int i =0; i < targetIndexes.size(); i++){
            if (lastShownEventList.size() < targetIndexes.get(i)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndexes.get(i) - 1));
        }
            return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownEventList.get(0).toString()));
        
    }

}