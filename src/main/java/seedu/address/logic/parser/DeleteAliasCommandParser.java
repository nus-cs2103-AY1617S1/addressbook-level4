package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.DeleteAliasCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses delete alias commands
 */
//@@author A0143107U
public class DeleteAliasCommandParser extends CommandParser{
	public static final String COMMAND_WORD = DeleteAliasCommand.COMMAND_WORD;

	/**
     * Parses arguments in the context of the delete alias command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		try{
	        return new DeleteAliasCommand(arguments);
		}
		catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
	}
}
