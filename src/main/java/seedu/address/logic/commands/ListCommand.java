package seedu.address.logic.commands;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Lists all tasks in the address book to the user.
 */
//@@author A0147890U
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List Task in Simply\n"
            +"To show completed Task :        Example: " + COMMAND_WORD + " done\n"
            +"To show incompleted Task :     Example: " + COMMAND_WORD +"\n";
    
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
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_USAGE));
        }
            
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
