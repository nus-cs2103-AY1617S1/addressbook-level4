package tars.logic.commands;

import java.util.HashSet;
import java.util.Set;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Lists all tasks in tars to the user.
 * 
 * @@author A0140022H
 */
public class ListCommand extends Command {

	public static final String COMMAND_WORD = "ls";

	public static final String MESSAGE_SUCCESS = "Listed all undone tasks";
	public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
	public static final String MESSAGE_SUCCESS_DONE = "Listed all done tasks";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Lists all task with the specified keywords and displays them as a list with index numbers.\n"
			+ "Parameters: [KEYWORD] " + "Example: " + COMMAND_WORD + " -do";

	private static final String LIST_ARG_DONE = "-do";
	private static final String LIST_ARG_ALL = "-all";
	private static final String LIST_KEYWORD_DONE = "done";
	private static final String LIST_KEYWORD_UNDONE = "undone";

	private Set<String> keyword;

	public ListCommand() {
		keyword = new HashSet<String>();
		keyword.add(LIST_KEYWORD_UNDONE);
	}

	public ListCommand(Set<String> arguments) {
		this.keyword = arguments;
	}

	@Override
	public CommandResult execute() {
		if(!keyword.contains(LIST_KEYWORD_UNDONE)){
			if (keyword.contains(LIST_ARG_DONE)) {
				keyword.add(LIST_KEYWORD_DONE);
				model.updateFilteredTaskList(keyword);
				return new CommandResult(MESSAGE_SUCCESS_DONE);
			} else if (keyword.contains(LIST_ARG_ALL)) {
				model.updateFilteredListToShowAll();
				return new CommandResult(MESSAGE_SUCCESS_ALL);
			} else {
				keyword.clear();
				keyword.add(LIST_KEYWORD_UNDONE);
				model.updateFilteredTaskList(keyword);
				return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
			}
		} else {
			model.updateFilteredTaskList(keyword);
			return new CommandResult(MESSAGE_SUCCESS);
		}
	}
}
