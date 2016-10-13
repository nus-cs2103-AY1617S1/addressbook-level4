package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;

public class ListParser implements Parser {

	private static final Pattern LIST_ARGS_FORMAT = Pattern.compile("(?<type>-t|-e)" + "(?: (?<showAll>-a))*");
	private static final String LIST_TYPE_TASK = "-t";
	private static final String LIST_TYPE_EVENT = "-e";

	@Override
	public Command prepare(String args) {
		final Matcher matcher = LIST_ARGS_FORMAT.matcher(args.trim());

		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE));
		}

		boolean showAll = (matcher.group("showAll") == null) ? false : true;

		switch (matcher.group("type")) {
		case LIST_TYPE_TASK:
			return new ListTaskCommand(showAll);
		case LIST_TYPE_EVENT:
			return new ListEventCommand(showAll);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE));
		}
	}

}
