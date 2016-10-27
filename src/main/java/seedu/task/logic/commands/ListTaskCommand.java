package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.taskcommons.core.LogsCenter;

//@@author A0144702N
/**
 * Lists all tasks in the task book to the user.
 */
public class ListTaskCommand extends ListCommand {
	private final Logger logger = LogsCenter.getLogger(ListTaskCommand.class); 
	public static final String MESSAGE_INCOMPLETED_SUCCESS = "Listed undone tasks";
	public static final String MESSAGE_ALL_SUCCESS = "Listed all tasks";
	
	private static final Boolean STATUS_INCOMPLETED = false;
	private static final String COMMAND_LOG_FORMAT = "[%1$s]";

	public ListTaskCommand(boolean showAll) {
		this.showAll = showAll;
	}
	
	@Override
	/**
	 * Executes a list task operation and updates the model
	 * 
	 * @return successful command execution feedback to user
	 */
	public CommandResult execute() {
		logger.info("-------[Executing ListTaskCommands]"+ this.toString());
		if (!shouldShowAll()) {
			model.updateFilteredTaskListToShowWithStatus(STATUS_INCOMPLETED);
			return new CommandResult(MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			model.updateFilteredTaskListToShowAll();
			return new CommandResult(MESSAGE_ALL_SUCCESS);
		}
	}
	
	@Override
	public String toString() {
		return String.format(COMMAND_LOG_FORMAT, (showAll)? "showing all" : "show only completed");
	}

}
