package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.events.ui.ShowHelpRequestEvent;
import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

	public static final String COMMAND_WORD = "help";

	public static final String add = "add";

	public static final String delete = "delete";

	public static final String edit = "edit";

	public static final String find = "find";

	public static final String all = "all";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
			+ COMMAND_WORD;

	public static final String HELP_MESSAGE_ALL = "To add, type: \n" + "add \"your own task name\" \n"
			+ "To delete, type: delete \"the specified index\" \n" + "To list, type: list \n";

	public static final String HELP_MESSAGE_ADD = "To add a task, type: \n"+"add \"your own task name\" \n"
			+ "d/ \"your deadline (optional)\" \n" + "s/ \"your start time (optional)\" \n"
			+ "e/ \"your end time (optional)\" \n" + "t/ \"your tags (optional)\" \n";

	public static final String HELP_MESSAGE_DELETE = "To delete a task, type: \n"+"\"list\" to list out all tasks \n"
			+ "\"delete\" \'index of the task to be delete\'\" \n";

	public static final String HELP_MESSAGE_FIND = "To find a task, type: \"find \'name of task\'\" \n"
			+ "OR type: \"find \'deadline of task\' \" \n" + "OR type: \"find today\" to find tasks due today \n"
			+ "OR type: \"find tomorrow\" to find tasks due tomorrow\n";

	public static final String HELP_MESSAGE_EDIT = "To find a task, type: \n"+"\"edit \'index of task\'\" \n"
			+ "d/ \"your new deadline(optional)\" \n" + "s/ \"your new start time\" \n" + "e/ \"your new end time\" \n";

	public static final String HELP_MESSAGE_HELP = "To find out how to add a task, type: \"help add\" \n"
			+ "To find out how to delete a task, type: \"help delete\" \n"
			+ "To find out how to find a task, type: \"help find\" \n"
			+ "To find out how to edit a task, type: \"help edit\" \n";

	private final String whichCommand;

	public HelpCommand(String whichCommand) {
		this.whichCommand = whichCommand;
	}

	@Override
	public CommandResult execute() {
		EventsCenter.getInstance().post(new ShowHelpRequestEvent());
		switch (whichCommand) {
		case add:
			return new CommandResult(HELP_MESSAGE_ADD);
		case delete:
			return new CommandResult(HELP_MESSAGE_DELETE);
		case find:
			return new CommandResult(HELP_MESSAGE_FIND);
		case edit:
			return new CommandResult(HELP_MESSAGE_EDIT);
		case all:
			return new CommandResult(HELP_MESSAGE_ALL);
		default:
			return new CommandResult(HELP_MESSAGE_HELP);
		}
	}
}
