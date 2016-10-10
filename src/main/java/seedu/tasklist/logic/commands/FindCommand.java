package seedu.tasklist.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in task list whose title contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names/date/description contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS1010";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
