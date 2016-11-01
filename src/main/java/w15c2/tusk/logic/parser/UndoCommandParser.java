package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UndoTaskCommand;

/**
 * Undoes the last command
 */
//@@author A0139817U
public class UndoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = UndoTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = UndoTaskCommand.ALTERNATE_COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTaskCommand.HELP_MESSAGE_USAGE));
        }
        return new UndoTaskCommand();
    }
}