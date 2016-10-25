package seedu.address.logic.parser;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.taskcommands.HelpTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;


/**
 * Parses Help commands
 */
//@@author A0139708W
public class HelpCommandParser extends CommandParser {
    public static final String COMMAND_WORD = HelpTaskCommand.COMMAND_WORD;
    
    /**
    * Parses arguments in the context of the find task command.
    *
    * @param args full command args string
    * @return the prepared command
    */

    @Override
    public TaskCommand prepareCommand(String arguments) {
        if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpTaskCommand.MESSAGE_USAGE));
        }
        return new HelpTaskCommand();
    }
}
