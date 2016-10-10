package seedu.task.logic.commands;

/**
 * Abstract class to represent generic list operations.  
 * @author xuchen
 */
public abstract class ListCommand extends Command {
	public static final String COMMAND_WORD = "list";
	
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
