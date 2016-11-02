package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.events.ui.JumpToListRequestEvent;
import seedu.ggist.commons.events.ui.ChangeListingEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.Priority;
import seedu.ggist.model.task.TaskDate;
//@@author A0138411N
/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives preferred tasks listing in GGist.\n"
            + "Parameter: [all] , [done] , [low/med/high] or [DATE]\n"
            + "Empty paramter lists all undone tasks in GGist\n"
            + "Example: " + COMMAND_WORD + " done or " + COMMAND_WORD + " 13 Oct/monday/today";
    private final static String LIST_ARGS_VALIDATION = "(all)|(done)|(high)|(med)|(low)|(\\w{3}, \\d{2} \\w{3} \\d{2})"; 
    
    private String listing;
        
    public ListCommand(String argument) throws IllegalValueException {
        assert argument != null;
        listing = argument;
    }
    
    /**
     * Validates the listing argument
     * @param String argument
     * @return true if is valid list argument
     */
    public static boolean isValidListArgs(String test) {
        return test.matches(LIST_ARGS_VALIDATION);
    }
    
    /**
     * Updates filtered task list in Model Manager according to the listing
     * Post an event to indicate listing changed
     */
    @Override
    public CommandResult execute() {
        if (listing.equals("all")) {
            model.updateFilteredListToShowAll();
        } else if (listing.equals("done")) {
            model.updateFilteredListToShowAllDone();
        } else if (listing.equals(Priority.PriorityType.LOW.toString()) || listing.equals(Priority.PriorityType.MEDIUM.toString()) 
                   || listing.equals(Priority.PriorityType.HIGH.toString())) {
            model.updateFilteredListToPriority(listing);
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
