package seedu.task.logic.commands;

import java.util.Locale;
import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks and events whose names and descriptions contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2103 Project";

	private static final String MESSAGE_SUCCESS_FIND = "%1$s\n%2$s";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        model.updateFilteredEventList(keywords);
        
        //TODO: check on coding convention
        return new CommandResult(String.format(MESSAGE_SUCCESS_FIND, 
        		getMessageForTaskListShownSummary(model.getFilteredTaskList().size()),
        		getMessageForEventListShownSummary(model.getFilteredEventList().size()))
        		);
    }

}
