package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.commands.RedoCommand;

//@@author A0139817U
/**
 * Parses arguments and create an redo command.
 */
public class RedoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = RedoCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = RedoCommand.ALTERNATE_COMMAND_WORD;

    /**
     * Parses arguments in the context of the redo command.
     *
     * @param arguments 	Expected to be empty.
     * @return 				A prepared redo command.
     */
    @Override
    public Command prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand();
    }
}