package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;
//@@author A0144702N
/**
 * Parses list command argument
 * @author xuchen
 *
 */
public class ListParser implements Parser {

	@Override
	public Command prepare(String args) {
		//empty field is not allowed
		if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(taskPresencePrefix, eventPresencePrefix, allPrefix);
		argsTokenizer.tokenize(args.trim());
		boolean showEvent = argsTokenizer.hasPrefix(eventPresencePrefix);
		boolean showTask = argsTokenizer.hasPrefix(taskPresencePrefix);
		boolean showAll = argsTokenizer.hasPrefix(allPrefix);
		
		//list with both flags are not supported
		if(showEvent && showTask) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		}
		
		if(showEvent) {
			return new ListEventCommand(showAll);
		} else if (showTask) {
			return new ListTaskCommand(showAll);
		} else {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		}
	}
}
