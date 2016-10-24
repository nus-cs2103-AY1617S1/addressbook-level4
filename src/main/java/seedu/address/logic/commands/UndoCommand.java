package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;

/**
 * Lists all persons in the address book to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": UndoNode the last reversible action in the task book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid last action %1$s";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        //model.resetData(TaskBook.getEmptyAddressBook());
        //return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
