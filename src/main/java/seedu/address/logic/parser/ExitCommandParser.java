package seedu.address.logic.parser;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.taskcommands.ExitCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;


/**
 * Parses Exit commands
 */
//@@author A0143107U
public class ExitCommandParser extends CommandParser {
    public static final String[] COMMAND_WORD = ExitCommand.COMMAND_WORD;
    
    /**
    * Parses arguments in the context of the exit command.
    *
    * @param args full command args string
    * @return the prepared command
    */

    @Override
    public TaskCommand prepareCommand(String arguments) {
        if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
        }
        return new ExitCommand();
    }
}
