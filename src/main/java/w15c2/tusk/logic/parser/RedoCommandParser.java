package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.RedoTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

/**
 * Redoes the last command
 */
//@@author A0139817U
public class RedoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = RedoTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = RedoTaskCommand.ALTERNATE_COMMAND_WORD;


    @Override
    public TaskCommand prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoTaskCommand.HELP_MESSAGE_USAGE));
        }
        return new RedoTaskCommand();
    }
}