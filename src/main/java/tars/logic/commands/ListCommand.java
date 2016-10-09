package tars.logic.commands;

import java.util.Set;

/**
 * Lists all tasks in tars to the user.
 */
public class ListCommand extends Command {

	public static final String COMMAND_WORD = "list";
	
	public static final String COMMAND_WORD_ADVANCE = "ls";

	public static final String MESSAGE_SUCCESS = "Listed all tasks";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Lists all task with the specified keywords and displays them as a list with index numbers.\n"
			+ "Parameters: [KEYWORD] " + "Example: " + COMMAND_WORD + "/" + COMMAND_WORD_ADVANCE + " undone";

	private Set<String> keyword;

	public ListCommand() {};

	public ListCommand(Set<String> arguments) {
		this.keyword = arguments;
	}

	@Override
	public CommandResult execute() {
		if (keyword != null && !keyword.isEmpty()) {
			if (keyword.contains("undone") || keyword.contains("done")) {
				model.updateFilteredTaskList(keyword);
				return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
			}
		}

		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
