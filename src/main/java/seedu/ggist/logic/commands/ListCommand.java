package seedu.ggist.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.ggist.logic.parser.Parser;
import seedu.ggist.model.task.TaskDate;

/**
 * Lists specified tasks in GGist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": retreives incomplete, completed or all task in GGist.\n "
            + "Parameter: [all] , [done] , [DATE]\n"
            + "Empty paramter lists all incomplete task in GGist\n"
            + "Example: " + COMMAND_WORD + " done or " + COMMAND_WORD + "13 Oct";
    
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
        } else if (TaskDate.isValidDate(listing)) {
//            String[] keywords = listing.split("\\s+");
//            Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            model.updateFilteredTaskListToShowDate(listing);
        } else {
            model.updateFilteredTaskListToShowUndone();
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
