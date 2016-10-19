package seedu.address.logic.commands;


/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowAllCommand extends Command {

    public static final String COMMAND_WORD = "showall";

    public static final String MESSAGE_SUCCESS = "Shown all tasks";

    public ShowAllCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
