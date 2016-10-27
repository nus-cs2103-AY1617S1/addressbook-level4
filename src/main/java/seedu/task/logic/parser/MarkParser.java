package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.MarkCommand;

/**
 * Responsible for validating and preparing the arguments for MarkCommand execution
 * @@author A0121608N
 */

public class MarkParser implements Parser {
    private static final Pattern MARK_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[1-9]{1}\\d*$)");
	
    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command prepare(String args) {
	    final Matcher markMatcher = MARK_ARGS_FORMAT.matcher(args.trim());
        if(!markMatcher.matches()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }else{
            int index = Integer.parseInt(markMatcher.group("targetIndex"));
            return new MarkCommand(index);
        }
	}

}
