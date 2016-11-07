package w15c2.tusk.logic.parser;


import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.HelpCommand;
import w15c2.tusk.logic.commands.IncorrectCommand;

//@@author A0139708W
/**
 * Parses Help commands
 */
public class HelpCommandParser extends CommandParser {
    public static final String COMMAND_WORD = HelpCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = HelpCommand.ALTERNATE_COMMAND_WORD;
    
    /**
    * Parses arguments in the context of the find task command.
    *
    * @param arguments   Arguments of help command.
    * @return            Prepared help command.
    */
    @Override
    public Command prepareCommand(String arguments) {
        if(!arguments.equals("")){
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        return new HelpCommand();
    }
}
