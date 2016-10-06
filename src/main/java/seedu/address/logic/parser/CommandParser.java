package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.TaskCommand;

public abstract class CommandParser {
	
	public abstract TaskCommand prepareCommand(String arguments);

}
