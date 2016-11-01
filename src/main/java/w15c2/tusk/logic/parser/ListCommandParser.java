package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

/*
 * Parses the list command
 */
//@@author A0139708W
public class ListCommandParser extends CommandParser{
    public static final String COMMAND_WORD = ListTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = ListTaskCommand.ALTERNATE_COMMAND_WORD;


    @Override
    public TaskCommand prepareCommand(String arguments) {
        try{
        	return new ListTaskCommand(arguments);
        }
        catch(IllegalValueException ive){
            return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE));
        }
    }

}
