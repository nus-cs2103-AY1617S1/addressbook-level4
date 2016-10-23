package seedu.cmdo.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive)," + "\n" + "/priorities and/or completion status, displaying them as a list with index numbers.\n"
            + "Parameters: <keyword> <more keywords>\n"
            + "Example: " + COMMAND_WORD + " dog";

    private final Set<String> keywords;
    private final boolean taskStatus;

    public FindCommand(Set<String> keywords, boolean taskStatus) {
        this.keywords = keywords;
        this.taskStatus = taskStatus;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, taskStatus);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
