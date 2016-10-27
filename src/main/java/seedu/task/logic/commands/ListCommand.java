package seedu.task.logic.commands;

//@@author A0144702N
/**
 * Abstract class to represent generic list operations.  
 * @author xuchen
 */

public abstract class ListCommand extends Command {
	public static final String COMMAND_WORD = "list";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
			+ COMMAND_WORD + " /t "
			+ "Shows a list of tasks that are not marked done\n"
			+ "Optional flag: [/a] to request show all tasks" 
			+ "Parameters: LIST_TYPE + [OPTIONAL FLAG]\n" 
			+ "Example: "+ COMMAND_WORD + " /t /a\n\n"
			+ COMMAND_WORD + " /e " 
			+ "Shows a list of events that are not completed yet.\n "
			+ "Optional flag: [/a] to request show all events" 
			+ "Parameters: LIST_TYPE + [OPTIONAL FLAG]\n" 
			+ "Example: "+ COMMAND_WORD + " /e /a";

	
	
	/** fields to indicate if all items should be displayed **/
	protected boolean showAll;
	
	/**
	 * Executes the command and returns the result message.
	 * @return feedback message of the operation result for display
	 */
	public abstract CommandResult execute();
	
	/**
	 * Determine if the list operations should show all items. 
	 * @return if all items should be shown
	 */
	protected boolean shouldShowAll() {
		return this.showAll;
	}
}
