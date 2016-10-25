package seedu.address.logic.commands;


/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final String keyword;

    public ListCommand(String args) {
        this.keyword = args;
    }

    @Override
    public CommandResult execute() {
        if (keyword.equals("all") || keyword.equalsIgnoreCase("")) {
            model.updateFilteredListToShowAll();
        }
        else if (keyword.equals("completed")) {
            model.updateFilteredListToShowAllCompleted();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
