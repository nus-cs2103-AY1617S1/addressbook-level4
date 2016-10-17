package seedu.ggist.logic.commands;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.TaskDate;

/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives incomplete, completed or all task in GGist.\n"
            + "Parameter: [all] , [done] or [DATE]\n"
            + "Empty paramter lists all incomplete task in GGist\n"
            + "Example: " + COMMAND_WORD + " done or " + COMMAND_WORD + " 13 Oct";
    private final static String LIST_ARGS_VALIDATION = "(all)|(done)|(\\w{3}, \\d{2} \\w{3} \\d{2})"; 
    
    private String listing;
    
    public ListCommand(String argument) throws IllegalValueException {
        listing = argument;
    }
    
    public static boolean isValidListArgs(String test) {
        return test.matches(LIST_ARGS_VALIDATION);
    }
    

    @Override
    public CommandResult execute() {
        if (listing.equals("all")) {
            model.updateFilteredListToShowAll();
        } else if (listing.equals("done")) {
            model.updateFilteredListToShowAllDone();
        } else if (TaskDate.isValidDateFormat(listing)) {
            model.updateFilteredTaskListToShowDate(listing);
        } else {
            model.updateFilteredTaskListToShowUndone();
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
