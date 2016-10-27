package seedu.agendum.logic.commands;


/**
 * Lists all tasks in the to do list to the user.
 */
public class ListCommand extends Command {

 // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_FORMAT = "list \n";
    public static final String COMMAND_DESCRIPTION = "list all your tasks";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
