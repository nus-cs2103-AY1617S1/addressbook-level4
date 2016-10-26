package seedu.address.logic.commands;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final String keyword;

    public ListCommand(String args) {
        this.keyword = args.trim();
    }

    @Override
    public CommandResult execute() {
            model.updateFilteredListToShowAllUncompleted();
            if (keyword.equals("all") || keyword.equalsIgnoreCase("")) {
        }
        else if (keyword.equals("done")) {
            model.updateFilteredListToShowAllCompleted();
        } else {
            return new CommandResult(MESSAGE_INVALID_COMMAND_FORMAT);
        }
            
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
