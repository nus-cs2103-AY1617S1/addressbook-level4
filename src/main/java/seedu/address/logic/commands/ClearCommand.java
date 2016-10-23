package seedu.address.logic.commands;

import seedu.address.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Simply has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.addToUndoStack();
        model.resetData(TaskBook.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
