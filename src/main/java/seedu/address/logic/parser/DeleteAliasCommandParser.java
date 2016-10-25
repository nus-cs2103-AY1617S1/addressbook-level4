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
	public static final String MESSAGE_INVALID_ARGUMENT = "You should only provide 1 alias";

	/**
     * Parses arguments in the context of the delete alias command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		// There should only be 1 word (no spaces)
		int space = arguments.trim().indexOf(" ");
		if (space != -1) {
			return new IncorrectTaskCommand(MESSAGE_INVALID_ARGUMENT);
		}
		
		try{
	        return new DeleteAliasCommand(arguments.trim());
		}
		catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
	}
}
