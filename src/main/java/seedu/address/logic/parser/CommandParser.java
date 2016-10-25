package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Defines a generic parser for commands
 */
//@@author A0143107U
public abstract class CommandParser {
	
	public abstract TaskCommand prepareCommand(String arguments);

}
