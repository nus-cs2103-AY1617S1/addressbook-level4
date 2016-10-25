package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keyphrases.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks containing any of "
            + "the specified keyphrases (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYPHRASE_WORD_1 [KEYPHRASE_WORD_2, ] [MORE_KEYPHRASES...]\n"
            + "Example: " + COMMAND_WORD + " dinner with wife, report";

    private final Set<String> keyphrases;

    public FindCommand(Set<String> keyphrases) {
        this.keyphrases = keyphrases;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keyphrases);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
