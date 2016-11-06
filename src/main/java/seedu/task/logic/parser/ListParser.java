package seedu.task.logic.parser;


import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListCommand;
import seedu.taskcommons.core.Status;

//@@author A0144702N
/**
 * Parses list command argument
 * @author xuchen
 *
 */
public class ListParser implements Parser {

	@Override
	public Command prepare(String args) {
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(taskPresencePrefix, eventPresencePrefix, allPrefix);
		argsTokenizer.tokenize(args.trim());
		boolean showEvent = argsTokenizer.hasPrefix(eventPresencePrefix);
		boolean showTask = argsTokenizer.hasPrefix(taskPresencePrefix);
		boolean showAll = argsTokenizer.hasPrefix(allPrefix);
		Status filter = (showAll) ? Status.NONE : Status.INCOMPLETED;
		if(showEvent && !showTask) {
			return new ListCommand(ListMode.EVENT, filter);
		} else if (showTask && !showEvent) {
			return new ListCommand(ListMode.TASK, filter);
		} else {
			return new ListCommand(ListMode.ALL, filter);
		}
	}
	
	/**
	 * Represents which list to be listed
	 * @author xuchen
	 */
	public enum ListMode {
		TASK("tasks"), EVENT("events"), ALL("tasks and events");
		
		private final String value;
		private ListMode(String value) {
			this.value = value;
		}
		@Override
		
		public String toString() {
			return this.value;
		}
		
	}
}
