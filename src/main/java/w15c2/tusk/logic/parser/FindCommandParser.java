package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import w15c2.tusk.logic.commands.taskcommands.FindTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Find commands
 */
public class FindCommandParser extends CommandParser{
	public static final String COMMAND_WORD = FindTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = FindTaskCommand.ALTERNATE_COMMAND_WORD;
	
	 /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public TaskCommand prepareCommand(String arguments) {
        // keywords delimited by whitespace
    	if(arguments.equals("")){
    		return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    	}
        final String[] keywords = arguments.split(" ");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindTaskCommand(keywordSet);
    }
}
