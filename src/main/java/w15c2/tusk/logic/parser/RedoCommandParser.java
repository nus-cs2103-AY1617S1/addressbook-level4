package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.RedoCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

//@@author A0139817U
/**
 * Redoes the last command
 */
public class RedoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = RedoCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = RedoCommand.ALTERNATE_COMMAND_WORD;


    @Override
    public TaskCommand prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand();
    }
}