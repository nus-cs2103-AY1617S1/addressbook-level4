package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses the list command
 */
public class ListCommandParser extends CommandParser{
    public static final String COMMAND_WORD = ListTaskCommand.COMMAND_WORD;

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
