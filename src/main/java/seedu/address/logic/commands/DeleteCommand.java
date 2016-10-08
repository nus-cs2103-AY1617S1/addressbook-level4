package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.item.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the taskname.\n"
            + "Parameters: TASKNAME \n"
            + "Example: " + COMMAND_WORD + " do homework";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Item: %1$s";

    /*
     * Deletes deadline, task, or event by keyword.
     */
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyItem personToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteItem(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

}
