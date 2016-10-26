package seedu.agendum.logic.commands;


/**
 * Lists all tasks in the to do list to the user.
 */
public class ListCommand extends Command {

 // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "list";
    public static String COMMAND_FORMAT = "list \n";
    public static String COMMAND_DESCRIPTION = "list all your tasks";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getName() {
        return COMMAND_WORD;
    }

    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
