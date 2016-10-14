package seedu.ggist.logic.commands;


/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives incomplete, completed or all task in GGist.\n "
            + "Parameter: [all] and [done]\n"
            + "Empty paramter lists all task in GGist\n"
            + "Example: " + COMMAND_WORD + "done";
    
    private final String listing;
    
    public ListCommand(String argument) {
        listing = argument;
    }

    @Override
    public CommandResult execute() {
        if (listing.equals("all")) {
            model.updateFilteredListToShowAll();
        } else if (listing.equals("done")) {
            model.updateFilteredListToShowAllDone();
        } else {
            model.updateFilteredTaskListToShowUndone();
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
