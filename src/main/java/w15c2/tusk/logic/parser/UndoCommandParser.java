package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.commands.UndoCommand;

//@@author A0139817U
/**
 * Parses arguments and create an undo command.
 */
public class UndoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = UndoCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = UndoCommand.ALTERNATE_COMMAND_WORD;

    /**
     * Parses arguments in the context of the undo command.
     *
     * @param arguments 	Expected to be empty.
     * @return 				A prepared undo command.
     */
    @Override
    public Command prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
        return new UndoCommand();
    }
}