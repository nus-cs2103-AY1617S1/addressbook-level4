package seedu.emeraldo.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in the task manager whose description contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindAllCommand extends Command {

    public static final String COMMAND_WORD = "findall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose description contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers, including completed tasks.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " homework lecture";

    private final Set<String> keywords;

    public FindAllCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListWithCompleted(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}