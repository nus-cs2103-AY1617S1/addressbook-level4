package seedu.malitio.logic.commands;


/**
 * Lists all tasks in Malitio to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
