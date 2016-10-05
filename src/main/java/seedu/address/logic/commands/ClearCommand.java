package seedu.address.logic.commands;

import seedu.address.model.ActivityManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ActivityManager.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
