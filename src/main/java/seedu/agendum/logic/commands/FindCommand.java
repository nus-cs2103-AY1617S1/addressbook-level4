package seedu.agendum.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in to do list whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

 // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_FORMAT= "find <keyword> \nfind <keyword> <more keywords>";
    public static final String COMMAND_DESCRIPTION = "search for task(s) based on keywords";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " star wars";

    private Set<String> keywords = null;
    
    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
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
