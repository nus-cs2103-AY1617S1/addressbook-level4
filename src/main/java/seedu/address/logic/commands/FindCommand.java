package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all task description that contain all of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " important meeting boss";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }
    //@@author A0139430L JingRui
    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(keywords);
        model.updateFilteredDeadlineList(keywords);
        model.updateFilteredTodoList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredEventList().size(), model.getFilteredDeadlineList().size(), model.getFilteredTodoList().size()));
    }

}
