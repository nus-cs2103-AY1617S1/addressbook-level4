package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class NotDoneCommand extends Command {

    public static final String COMMAND_WORD = "notdone";
  
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "nd";
    
    //@@author A0065571A
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the item identified by the index number used in the last item listing as not done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOT_DONE_SUCCESS = "Not Done Item: %1$s";

    public final int targetIndex;

    /*
     * Deletes deadline, task, or event by keyword.
     */
    public NotDoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyItem itemToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            model.setUndone(itemToEdit, String.format(MESSAGE_NOT_DONE_SUCCESS, itemToEdit));
        } catch (ItemNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_NOT_DONE_SUCCESS, itemToEdit));
    }

}
