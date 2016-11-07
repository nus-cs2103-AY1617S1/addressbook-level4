package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.MarkCommand;

/**
 * @@author A0121608N
 * Responsible for validating and preparing the arguments for MarkCommand execution
 * 
 */

public class MarkParser implements Parser {
    private static final Pattern MARK_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\d+)");
    
    /**
     * Parses arguments in the context of the mark task command.
     *
     * Regex matcher will ensure that the string parsed into an integer is valid, 
     * hence there is no need to try and catch NumberFormatException
     * 
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command prepare(String args) {
	    final Matcher markMatcher = MARK_ARGS_FORMAT.matcher(args.trim());
        if(markMatcher.matches()){
            int index = Integer.parseInt(markMatcher.group("targetIndex"));
            if(index!=0){
                return new MarkCommand(index);
            }
        }
        return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));  
	}

}
