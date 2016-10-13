package seedu.ggist.logic.commands;


/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives completed tasks or shows all tasks.\n "
            + "Parameter: [LISTING]\n"
            + "Empty paramter lists all task in GGist\n"
            + "Example: " + COMMAND_WORD + "done";
    
    private final String listing;
    
    public ListCommand(String argument) {
        listing = argument;
    }

    @Override
    public CommandResult execute() {
        if (listing.equals("")) {
            model.updateFilteredListToShowAll();
        } else if (listing.equals("done")) {
            model.updateFilteredListToShowAllDone();
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
