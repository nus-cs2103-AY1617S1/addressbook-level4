package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

public class IncorrectCommandParser extends CommandParser{
	
	public TaskCommand prepareCommand(String arguments) {
		return new IncorrectTaskCommand(MESSAGE_UNKNOWN_COMMAND);
	}
}
