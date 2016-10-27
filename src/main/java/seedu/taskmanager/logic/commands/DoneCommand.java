package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "do";
    public static final String ALTERNATE_SHORT_COMMAND_WORD = "d";
    
    //@@author A0065571A
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the item identified by the index number used in the last item listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_SUCCESS = "Done Item: %1$s";

    public final int targetIndex;

    /*
     * Deletes deadline, task, or event by keyword.
     */
    public DoneCommand(int targetIndex) {
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
            model.setDone(itemToEdit, String.format(MESSAGE_DONE_SUCCESS, itemToEdit));
        } catch (ItemNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DONE_SUCCESS, itemToEdit));
    }

}
