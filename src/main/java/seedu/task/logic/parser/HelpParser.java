package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.MarkCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;

/**
 * Responsible for validating and preparing the arguments for HelpCommand
 * execution
 * 
 * @author Yee Heng
 */

public class HelpParser implements Parser {

	public HelpParser() {
	}

	private static final Pattern HELP_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

	/**
	 * Parses arguments in the context of the help command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */

	public Command prepare(String args) {
		final Matcher matcher = HELP_ARGS_FORMAT.matcher(args.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		switch (matcher.group("arguments")) {

		case AddCommand.COMMAND_WORD:
			return new HelpCommand(AddCommand.MESSAGE_USAGE, false);
		case EditCommand.COMMAND_WORD:
            return new HelpCommand(EditCommand.MESSAGE_USAGE, false);
		case DeleteCommand.COMMAND_WORD:
			return new HelpCommand(DeleteCommand.MESSAGE_USAGE, false);
		case FindCommand.COMMAND_WORD:
			return new HelpCommand(FindCommand.MESSAGE_USAGE, false);
		case ListCommand.COMMAND_WORD:
			return new HelpCommand(ListCommand.MESSAGE_USAGE, false);
		case SelectCommand.COMMAND_WORD:
			return new HelpCommand(SelectCommand.MESSAGE_USAGE, false);
		case MarkCommand.COMMAND_WORD:
			return new HelpCommand(MarkCommand.MESSAGE_USAGE, false);
		case UndoCommand.COMMAND_WORD:
			return new HelpCommand(UndoCommand.MESSAGE_USAGE, false);
		case ClearCommand.COMMAND_WORD:
			return new HelpCommand(ClearCommand.MESSAGE_USAGE, false);
		case ExitCommand.COMMAND_WORD:
			return new HelpCommand(ExitCommand.MESSAGE_USAGE, false);
		default:
			return new HelpCommand(HelpCommand.MESSAGE_USAGE, true);
			
		}
	}
}
