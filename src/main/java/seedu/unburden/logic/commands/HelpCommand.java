package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.events.ui.ShowHelpRequestEvent;
import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Format full help instructions for every command for display.
 * @@author A0139678J
 */
public class HelpCommand extends Command {

	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
			+ COMMAND_WORD;

	public static final String HELP_MESSAGE_ADD = "To add a task, type: \n"+"add \"your own task name\" \n"
			+ "d/ \"your deadline (optional)\" \n" + "s/ \"your start time (optional)\" \n"
			+ "e/ \"your end time (optional)\" \n" + "t/ \"your tags (optional)\" \n";

	public static final String HELP_MESSAGE_DELETE = "To delete a task, type: \n"+"\"list\" to list out all tasks \n"
			+ "\"delete\" \'index of the task to be delete\' \" \n";

	public static final String HELP_MESSAGE_FIND = "To find a task, type: \"find \'name of task\' \" \n"
			+ "OR type: \"find \'deadline of task\' \" \n" + "OR type: \"find today\" to find tasks due today \n"
			+ "OR type: \"find tomorrow\" to find tasks due tomorrow\n";

	public static final String HELP_MESSAGE_EDIT = "To find a task, type: \n"+"\"edit \'index of task\' \" \n"
			+ "d/ \"your new deadline(optional)\" \n" + "s/ \"your new start time\" \n" + "e/ \"your new end time\" \n";
	
	public static final String HELP_MESSAGE_CLEAR = "To delete all tasks from Unburden, type \"clear\" ";
	
	public static final String HELP_MESSAGE_EXIT = "To exit Unburden, type \"exit\" ";
	
	public static final String HELP_MESSAGE_LIST = "To list out all exisiting tasks in address in Unburden, type \"list\" ";

	public static final String HELP_MESSAGE_HELP = "List of commands: \n"
			+ "1) Add : Allows you to add a task to Unburden \n"
			+ "2) Edit : Allows you to edit an existing task in Unburden \n"
			+ "3) Find : Allows you to find an exisiting task in Unburden based on task names or deadlines \n"
			+ "4) Delete : Allows you delete a single exisiting task task in Unburden \n"
			+ "5) List : Allows you to list all exisiting tasks in Unburden \n"
			+ "6) Clear : Allows you delete ALL tasks within Unburden \n"
			+ "7) Help : Allows you get some information on how to use the application \n"
			+ "8) Exit : Allows you exit and close the application \n";

	private final String whichCommand;

	public HelpCommand(String whichCommand) {
		this.whichCommand = whichCommand;
	}

	@Override
	public CommandResult execute() {
		EventsCenter.getInstance().post(new ShowHelpRequestEvent());
		switch (whichCommand) {
		case AddCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_ADD);
		case DeleteCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_DELETE);
		case FindCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_FIND);
		case EditCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_EDIT);
		case ListCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_LIST);
		case ClearCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_CLEAR);
		case ExitCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_EXIT);
		default:
			return new CommandResult(HELP_MESSAGE_HELP);
		}
	}
}
