package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.ui.PersonListPanel;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number given in the most recent listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();

        if ((targetIndex <= PersonListPanel.UNDATED_DISPLAY_INDEX_OFFSET 
                && lastUndatedTaskList.size() < targetIndex)
           || (targetIndex > PersonListPanel.UNDATED_DISPLAY_INDEX_OFFSET 
                   && lastShownList.size() < targetIndex - PersonListPanel.UNDATED_DISPLAY_INDEX_OFFSET)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask personToDelete;
        if (targetIndex > PersonListPanel.UNDATED_DISPLAY_INDEX_OFFSET) {
            personToDelete = lastShownList.get(targetIndex - 1 - PersonListPanel.UNDATED_DISPLAY_INDEX_OFFSET);
        }
        else {
            personToDelete = lastUndatedTaskList.get(targetIndex - 1);
        }

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

}
