package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.ClearCommand.Type;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * @@author A0121608N
 * Responsible for validating and preparing the arguments for ClearCommand execution
 * 
 */
public class ClearParser implements Parser {

	private static final Pattern CLEAR_ARGS_FORMAT = Pattern.compile("(?<clearType>(?:(/t|/e|/a))*)" + "(?: (?<clearAll>/a))*");
	private static final String CLEAR_TYPE_TASK = "/t";
	private static final String CLEAR_TYPE_EVENT = "/e";
	private static final String CLEAR_TYPE_ALL = "/a";
	private static final String CLEAR_TYPE_EMPTY = "";
	
    /**
     * Parses arguments in the context of the ClearCommand.
     * Type is a public enumerator defined in ClearCommand
     *
     * @param args full command args string
     * @return the prepared command
     */

	@Override
	public Command prepare(String args) {
		final Matcher matcher = CLEAR_ARGS_FORMAT.matcher(args.trim());

		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}
		
		boolean clearAll = (matcher.group("clearAll") == null) ? false : true;
		
		switch (matcher.group("clearType")) {
		case CLEAR_TYPE_EMPTY: // "clear"
		    return new ClearCommand(Type.all, false);
		case CLEAR_TYPE_TASK: // "clear /t" & "clear /t /a"
			return new ClearCommand(Type.task, clearAll); 
		case CLEAR_TYPE_EVENT: // "clear /e" & "clear /e /a"
			return new ClearCommand(Type.event, clearAll);
		case CLEAR_TYPE_ALL: // "clear /a"
		    return new ClearCommand(Type.all, true);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}
	}

}
