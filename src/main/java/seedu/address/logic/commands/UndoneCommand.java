package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.item.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the item identified by the index number used in the last item listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_SUCCESS = "Done Item: %1$s";

    public final int targetIndex;

    /*
     * Deletes deadline, task, or event by keyword.
     */
    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyItem itemToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            model.setUndone(itemToEdit);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DONE_SUCCESS, itemToEdit));
    }

}
