package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.model.item.ReadOnlyItem;

//@@author A0140060A-reused
/**
 * Selects an item identified using it's last displayed index from the task manager
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "s";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ITEM_SUCCESS = "Selected %1$s";
    //@@author 
    
    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_ITEM_SUCCESS, targetIndex));

    }

}
