package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Alias commands
 */
public class AliasCommandParser extends CommandParser{
    public static final String COMMAND_WORD = AddAliasCommand.COMMAND_WORD;
    
	/**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String alias) {
		int space = alias.indexOf(" ");
		String shortcut = alias.substring(0, space);
		String sentence = alias.substring(space + 1);
		
		try {
			return new AddAliasCommand(shortcut, sentence);
		} catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
	
	}
}
