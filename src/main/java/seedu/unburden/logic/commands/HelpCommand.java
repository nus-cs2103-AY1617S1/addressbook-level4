package seedu.unburden.logic.commands;

import java.util.List;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.events.ui.ShowHelpRequestEvent;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;

/**
 * Format full help instructions for every command for display.
 * 
 * @@author A0139678J
 */
// @@Nathanael Chan A0139678J
public class HelpCommand extends Command {

	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
			+ COMMAND_WORD;

	public static final String HELP_MESSAGE_ADD = "To add a task, type: \n" + "add \"your own task name\" \n"
			+ "d/ \"your deadline (optional)\" \n" + "s/ \"your start time (optional)\" \n"
			+ "e/ \"your end time (optional)\" \n" + "t/ \"your tags (optional)\" \n";

	public static final String HELP_MESSAGE_DELETE = "To delete a task, type: \n" + "\"list\" to list out all tasks \n"
			+ "\"delete\" \'index of the task to be delete\' \" \n";

	public static final String HELP_MESSAGE_FIND = "To find a task, type: \"find \'name of task\' \" \n"
			+ "OR type: \"find \'deadline of task\' \" \n" + "OR type: \"find today\" to find tasks due today \n"
			+ "OR type: \"find tomorrow\" to find tasks due tomorrow\n";

	public static final String HELP_MESSAGE_EDIT = "To find a task, type: \n" + "\"edit \'index of task\' \" \n"
			+ "d/ \"your new deadline(optional)\" \n" + "s/ \"your new start time\" \n" + "e/ \"your new end time\" \n";

	public static final String HELP_MESSAGE_CLEAR = "To delete all tasks from Unburden, type \"clear\" ";

	public static final String HELP_MESSAGE_EXIT = "To exit Unburden, type \"exit\" ";

	public static final String HELP_MESSAGE_LIST = "To list out all exisiting tasks in address in Unburden, type \"list\" ";

	public static final String HELP_MESSAGE_HELP = "List of commands: \n" + "1) Add : Adds a task to Unburden \n"
			+ "2) Edit : Edits an existing task in Unburden \n"
			+ "3) Find : Finds an exisiting task in Unburden based \n" + "on task names or deadlines \n"
			+ "4) Delete : Deletes a single exisiting \n" + "task task in Unburden \n"
			+ "5) List : Lists all exisiting tasks in Unburden \n" + "6) Clear : To delete ALL tasks within Unburden \n"
			+ "7) Undo : To Undo any previous command \n" + "8) Redo : To redo any previous command \n"
			+ "9) Done : Marks a task as done and \n" + "undone marks it as undone \n"
			+ "10) Help : To get some information on \n" + " how to use the application \n"
			+ "11) Exit : To exit and close the application \n";

	private static final String HELP_MESSAGE_UNDO = null;

	private static final String HELP_MESSAGE_REDO = null;

	private static final String HELP_MESSAGE_DONE = null;

	private static final String HELP_MESSAGE_UNDONE = null;

	private final String whichCommand;

	public HelpCommand(String whichCommand) {
		this.whichCommand = whichCommand;
	}

	@Override
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {
		overdueOrNot();
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
		case UndoCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_UNDO);
		case RedoCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_REDO);
		case DoneCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_DONE);
		case ExitCommand.COMMAND_WORD:
			return new CommandResult(HELP_MESSAGE_EXIT);
		default:
			return new CommandResult(HELP_MESSAGE_HELP);
		}
	}

	// This method checks the entire list to check for overdue tasks
	private void overdueOrNot() throws IllegalValueException, DuplicateTagException {
		List<ReadOnlyTask> currentTaskList = model.getListOfTask().getTaskList();
		for (ReadOnlyTask task : currentTaskList) {
			if (((Task) task).checkOverDue()) {
				((Task) task).setOverdue();
			} else {
				((Task) task).setNotOverdue();
			}
		}
	}
}
