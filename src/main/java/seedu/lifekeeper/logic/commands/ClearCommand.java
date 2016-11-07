package seedu.lifekeeper.logic.commands;

import seedu.lifekeeper.model.LifeKeeper;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Lifekeeper has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(LifeKeeper.getEmptyAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
