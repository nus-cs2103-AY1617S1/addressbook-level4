package seedu.task.logic.commands;

/**
 * Lists all events in the task book to the user. 
 * @author xuchen
 *
 */
public class ListEventCommand extends ListCommand {
	public static final String MESSAGE_INCOMPLETED_SUCCESS = "Listed up coming events";
	public static final String MESSAGE_ALL_SUCCESS = "Listed all events";
	private static final boolean STATUS_UPCOMING = false;
	
	
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
		if (!shouldShowAll()) {
			model.updateFilteredEventListToShowWithStatus(STATUS_UPCOMING);
			
			return new CommandResult(MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			model.updateFilteredEventListToShowAll();
			return new CommandResult(MESSAGE_ALL_SUCCESS);
		}
	}

}
