package seedu.unburden.logic.commands;

import seedu.unburden.model.ListOfTask;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Unburden has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.saveToPrevLists();
        model.resetData(ListOfTask.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
