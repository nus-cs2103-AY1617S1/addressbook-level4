package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class RedoCommand extends Command {

	//@@author A0065571A
    public static final String COMMAND_WORD = "redo";
    public static final String SHORT_COMMAND_WORD = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo last action that led to a change in Todo list"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DONE_SUCCESS = "Redo last undone action: %1$s";
    public static final String MESSAGE_DONE_FAILURE = "Cannot Redo";

    /*
     * Undo last command that led to a change in todo list.
     */
    public RedoCommand() {
    	
    }

    @Override
    public CommandResult execute() {

        String done = model.redoAction();
        // model.updateFilteredListToShowAll();
        if (done != null) {
            return new CommandResult(String.format(MESSAGE_DONE_SUCCESS, done));
        } else {
            return new CommandResult(MESSAGE_DONE_FAILURE);
        }
    }

}
