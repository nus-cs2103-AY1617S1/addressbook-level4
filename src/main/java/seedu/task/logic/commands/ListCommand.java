package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Status;
import seedu.task.logic.parser.ListParser.ListTarget;

//@@author A0144702N
/**
 * Represent  list operations.  
 * @author xuchen
 */

public class ListCommand extends Command {
	private final Logger logger = LogsCenter.getLogger(ListCommand.class);
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
			+ "Example: "+ COMMAND_WORD + " /e /a"
			+ COMMAND_WORD + " [/e /t]"
			+ "Shows both lists of upcoming tasks and events"				+ "Optional flag: [/a] to request shows all completed ones "
			+ "Parameters:[OPTIONAL FLAG]\n" 
			+ "Example: "+ COMMAND_WORD + "/a";
	public static final String MESSAGE_SUCCESS_FORMAT = "dowat is showing %1$s %2$s";
	
	/** which panel to list **/
	private ListTarget listTarget;
	/** fields to indicate items of which state should be displayed **/
	private Status status;
	
	public ListCommand(ListTarget targetPanel, Status filter) {
		this.listTarget = targetPanel;
		this.status = filter;
	}
	
	/**
	 * Executes the command and returns the result message.
	 * @return feedback message of the operation result for display
	 */
	public CommandResult execute() {
		logger.info("-------[Executing ListCommands]"+ this.toString() );
		
		switch (listTarget) {
		case EVENT:
			model.updateFilteredEventListToShowWithStatus(status);
			break;
		case TASK:
			model.updateFilteredTaskListToShowWithStatus(status);
			break;
		case BOTH:
			model.updateFilteredTaskListToShowWithStatus(status);
			model.updateFilteredEventListToShowWithStatus(status);
			break;
		default:
			return new CommandResult(MESSAGE_USAGE);
		}
		
		return new CommandResult(this.toString());
	}
	
	@Override
	public String toString() {
		return String.format(MESSAGE_SUCCESS_FORMAT,
						this.status, 
						listTarget.toString());
	}
}
