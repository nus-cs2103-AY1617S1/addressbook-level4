package tars.logic.commands;

import java.util.Set;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Lists all tasks in tars to the user.
 * 
 * @@author A0140022H
 */
public class ListCommand extends Command {

	public static final String COMMAND_WORD = "ls";

	public static final String MESSAGE_SUCCESS = "Listed all tasks";
	public static final String MESSAGE_SUCCESS_DATETIME = "Listed all tasks by datetime earliest endDate first";
	public static final String MESSAGE_SUCCESS_DATETIME_DESCENDING = "Listed all tasks by latest endDate first";
	public static final String MESSAGE_SUCCESS_PRIORITY = "Listed all tasks by priority from low to high";
	public static final String MESSAGE_SUCCESS_PRIORITY_DESCENDING = "Listed all tasks by priority from high to low";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Lists all task with the specified keywords and displays them as a list with index numbers.\n"
			+ "Parameters: [KEYWORD] " + "Example: " + COMMAND_WORD + " /dt";

	private static final String LIST_ARG_DATETIME = "/dt";
	private static final String LIST_ARG_PRIORITY = "/p";
	private static final String LIST_KEYWORD_DESCENDING = "dsc";

	private Set<String> keywords;

	public ListCommand() {
	}

	public ListCommand(Set<String> arguments) {
		this.keywords = arguments;
	}

	@Override
	public CommandResult execute() {
		if (keywords != null && !keywords.isEmpty()) {
			if (keywords.contains(LIST_ARG_DATETIME) || keywords.contains(LIST_ARG_PRIORITY)
					|| keywords.contains(LIST_KEYWORD_DESCENDING)) {

				model.sortFilteredTaskList(keywords);

				if (keywords.contains(LIST_KEYWORD_DESCENDING)) {
					if (keywords.contains(LIST_ARG_DATETIME))
						return new CommandResult(MESSAGE_SUCCESS_DATETIME_DESCENDING);
					else
						return new CommandResult(MESSAGE_SUCCESS_PRIORITY_DESCENDING);
				} else {
					if (keywords.contains(LIST_ARG_DATETIME))
						return new CommandResult(MESSAGE_SUCCESS_DATETIME);
					else
						return new CommandResult(MESSAGE_SUCCESS_PRIORITY);
				}
			} else {
				model.updateFilteredListToShowAll();
				return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
			}
		} else {
			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_SUCCESS);
		}
	}
}
