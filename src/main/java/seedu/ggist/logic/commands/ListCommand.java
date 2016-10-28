package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.events.ui.JumpToListRequestEvent;
import seedu.ggist.commons.events.ui.ChangeListingEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.TaskDate;
//@@author A0138411N
/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives undone, done, or all tasks in GGist.\n"
            + "Parameter: [all] , [done] or [DATE]\n"
            + "Empty paramter lists all undone tasks in GGist\n"
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
            model.updateFilteredListToShowDate(listing);
        } else {
            model.updateFilteredListToShowAllUndone();
        }
        model.setLastListing(listing);
        EventsCenter.getInstance().post(new ChangeListingEvent(listing));
        indicateCorrectCommandExecuted();
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
