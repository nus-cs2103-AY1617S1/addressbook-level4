package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.util.StringUtil;
import seedu.task.logic.LogicManager;
import seedu.taskcommons.core.LogsCenter;

//@@author A0144702N
/**
 * Lists all events in the task book to the user. 
 * @author xuchen
 *
 */
public class ListEventCommand extends ListCommand {
	private final Logger logger = LogsCenter.getLogger(ListEventCommand.class);
	public static final String MESSAGE_INCOMPLETED_SUCCESS = "Listed up coming events";
	public static final String MESSAGE_ALL_SUCCESS = "Listed all events";
	private static final boolean STATUS_UPCOMING = false;
	private static final String COMMAND_LOG_FORMAT = "[%1$s]";
	
	
	public ListEventCommand(boolean showAll) {
		this.showAll = showAll;
	}
	
	@Override
	/**
	 * Executes a list event operation and updates the model
	 * 
	 * @return successful command execution feedback to user
	 */
	public CommandResult execute() {
		logger.info("-------[Executing ListEventCommands]"+ this.toString() );
		if (!shouldShowAll()) {
			model.updateFilteredEventListToShowWithStatus(STATUS_UPCOMING);
			
			return new CommandResult(MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			model.updateFilteredEventListToShowAll();
			return new CommandResult(MESSAGE_ALL_SUCCESS);
		}
	}
	
	@Override
	public String toString() {
		return String.format(COMMAND_LOG_FORMAT, (showAll)? "showing all" : "show only completed");
	}

}
