package seedu.task.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskCommand extends ListCommand {

	public static final String MESSAGE_INCOMPLETED_SUCCESS = "Listed undone tasks";
	public static final String MESSAGE_ALL_SUCCESS = "Listed all tasks";
	
	private static final boolean STATUS_INCOMPLETED = false;

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
		if (!shouldShowAll()) {
			model.updateFilteredTaskListToShowWithStatus(STATUS_INCOMPLETED);
			return new CommandResult(MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_ALL_SUCCESS);
		}
	}
}
