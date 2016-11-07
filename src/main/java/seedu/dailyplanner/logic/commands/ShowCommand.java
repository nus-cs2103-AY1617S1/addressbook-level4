package seedu.dailyplanner.logic.commands;

import java.util.Set;

import seedu.dailyplanner.commons.util.StringUtil;

/**
 * Shows the task in the daily planner to the user depending on the keywords passed.
 */
// @@author A0146749N
public class ShowCommand extends Command {

	public static final String COMMAND_WORD = "show";

	public static final String MESSAGE_SUCCESS = "Showing %1$s tasks";

	private final Set<String> keywords;

	public ShowCommand() {
		keywords = null;
	}

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
			} else if(keywords.contains("not complete")) {
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
