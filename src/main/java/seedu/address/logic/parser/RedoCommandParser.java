package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.RedoTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/**
 * Redoes the last command
 */
//@@author A0139817U
public class RedoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = RedoTaskCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoTaskCommand.HELP_MESSAGE_USAGE));
        }
        return new RedoTaskCommand();
    }
}