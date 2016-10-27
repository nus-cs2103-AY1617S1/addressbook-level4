package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.ClearCommand.Type;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Responsible for validating and preparing the arguments for ClearCommand execution
 * @@author A0121608N
 */
public class ClearParser implements Parser {

	private static final Pattern CLEAR_ARGS_FORMAT = Pattern.compile("(?<type>(?:(/t|/e|/a))*)" + "(?: (?<isAll>/a))*");
	private static final String CLEAR_TYPE_TASK = "/t";
	private static final String CLEAR_TYPE_EVENT = "/e";
	private static final String CLEAR_TYPE_ALL = "/a";
	
	@Override
	public Command prepare(String args) {
		final Matcher matcher = CLEAR_ARGS_FORMAT.matcher(args.trim());

		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}
		
		boolean isAll = (matcher.group("isAll") == null) ? false : true;
		
		switch (matcher.group("type")) {
		case "": // "clear"
		    return new ClearCommand(Type.all, false);
		case CLEAR_TYPE_TASK: // "clear /t" & "clear /t /a"
			return new ClearCommand(Type.task, isAll); 
		case CLEAR_TYPE_EVENT: // "clear /e" & "clear /e /a"
			return new ClearCommand(Type.event, isAll);
		case CLEAR_TYPE_ALL: // "clear /a"
		    return new ClearCommand(Type.all, true);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}
	}

}
