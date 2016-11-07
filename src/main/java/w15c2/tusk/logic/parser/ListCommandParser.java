package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;

//@@author A0139708W
/**
 * Parses the List Command.
 */
public class ListCommandParser extends CommandParser{
    public static final String COMMAND_WORD = ListTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = ListTaskCommand.ALTERNATE_COMMAND_WORD;

    /**
     * Prepares ListTaskComamnd, catching 
     * IllegalValueExceptions to create an Incorrect
     * Command instance instead.
     * 
     * @param arguments     Arguments of list command.
     * @return              Prepared ListTaskCommand.
     */
    @Override
    public Command prepareCommand(String arguments) {
        try{
        	return new ListTaskCommand(arguments);
        }
        catch(IllegalValueException ive){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE));
        }
    }

}
