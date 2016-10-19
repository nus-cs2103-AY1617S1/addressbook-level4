package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyItem;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified by index as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example 1: " + COMMAND_WORD + " 1\n";

    public static final String MESSAGE_DONE_ITEM_SUCCESS = "Task marked as complete!";
    public static final String MESSAGE_DONE_ITEM_FAIL = "Task already marked as complete!";

    public final int targetIndex;

    public DoneCommand(int index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyItem itemToComplete = lastShownList.get(targetIndex - 1);
        
        if(itemToComplete.getIsDone()) {
            return new CommandResult(MESSAGE_DONE_ITEM_FAIL);
        } else {
            itemToComplete.setIsDone(true);
        }

        return new CommandResult(MESSAGE_DONE_ITEM_SUCCESS);
    }


}
