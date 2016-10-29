package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.taskcommands.FindTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Find commands
 */
public class FindCommandParser extends CommandParser{
	public static final String[] COMMAND_WORD = FindTaskCommand.COMMAND_WORD;
	
	 /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public TaskCommand prepareCommand(String arguments) {
        // keywords delimited by whitespace
        final String[] keywords = arguments.split(" ");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindTaskCommand(keywordSet);
    }
}
