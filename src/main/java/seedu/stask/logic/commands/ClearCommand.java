package seedu.stask.logic.commands;

import seedu.stask.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskBook.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
