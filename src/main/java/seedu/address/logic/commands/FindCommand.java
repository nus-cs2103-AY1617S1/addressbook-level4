package seedu.address.logic.commands;

import java.util.Set;

//@@author A0146123R
/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords or all of the argument keywords. Keyword matching is not
 * case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Case-insensitive. By default, finds all tasks with names containing any of "
            + "the specified keywords and the matching will only compare word stems of keywords.\n"
            + "Only events and tasks matching the exact keyword will be returned if the command contains the exact! parameter.\n"
            + "Only events and tasks matching both groups of keywords will be returned if the two groups of keywords are connected by AND.\n"
            + "Parameters: KEYWORD [AND] [MORE_KEYWORDS] [exact!]\n" 
            + "Example: " + COMMAND_WORD + " horror night, " + COMMAND_WORD + " horror AND night exact!";

    private final Set<Set<String>> keywordsGroups;
    private final boolean isExactSearch;

    public FindCommand(Set<Set<String>> keywordsGroups, boolean isExactSearch) {
        this.keywordsGroups = keywordsGroups;
        this.isExactSearch = isExactSearch;
    }

    @Override
    public CommandResult execute() {
        if (isExactSearch) {
            model.updateFilteredTaskListWithKeywords(keywordsGroups);
        } else {
            model.updateFilteredTaskListWithStemmedKeywords(keywordsGroups);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
