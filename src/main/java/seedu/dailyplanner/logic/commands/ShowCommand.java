package seedu.dailyplanner.logic.commands;

import java.util.Set;

import seedu.dailyplanner.commons.util.StringUtil;

/**
 * Shows the task in the daily planner to the user depending on the keywords
 * passed.
 */
// @@author A0146749N
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Showing %1$s tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all tasks whose completion status"
	    + "matches the keyword or whose start and end date range falls within the keyword date"
	    + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " completed";
    public static final String KEYWORD_SHOW_BY_COMPLETION = "complete";
    public static final String KEYWORD_SHOW_NOT_COMPLETED = "not complete";

    private final Set<String> keywords;

    public ShowCommand() {
	keywords = null;
    }

    // @@author A0146749N
    public ShowCommand(Set<String> keywords) {
	this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
	if (keywords == null) {
	    model.updateFilteredListToShowAll();
	    model.setLastShowDate(StringUtil.EMPTY_STRING);
	    return new CommandResult(String.format(MESSAGE_SUCCESS, "all"));
	} else {
	    if (keywords.contains("complete")) {
		model.updateFilteredTaskListByCompletion(keywords);
		model.setLastShowDate("completed");
	    } else if (keywords.contains("not complete")) {
		model.updateFilteredTaskListByCompletion(keywords);
		model.setLastShowDate("not completed");
	    } else {
		model.updateFilteredTaskListByDate(keywords);
		model.setLastShowDate((String) keywords.toArray()[0]);
	    }
	    return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredTaskList().size()));
	}
    }
}
