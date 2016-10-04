package seedu.address.logic.commands;


import seedu.address.commons.core.Messages;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(Messages.MESSAGE_CLEAR_FIND);
    }
}
