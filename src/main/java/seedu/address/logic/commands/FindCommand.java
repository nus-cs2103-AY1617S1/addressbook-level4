//@@author A0147995H
package seedu.address.logic.commands;

import java.util.Date;
import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " take trash ";

    private final Set<String> keywords;
    private final Date startTime;
    private final Date endTime;
    private final Date deadline;
    private final Set<String> tags;

    public FindCommand(Set<String> keywords, Date startTime, Date endTime, Date deadline, Set<String> tags) {
        this.keywords = keywords;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.tags = tags;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, tags, startTime, endTime, deadline);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskComponentList().size()));
    }

}
//@@author
