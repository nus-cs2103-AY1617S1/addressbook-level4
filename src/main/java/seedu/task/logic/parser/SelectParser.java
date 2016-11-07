package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SelectEventCommand;
import seedu.task.logic.commands.SelectTaskCommand;

//@@author A0125534L

/**
 * Responsible for validating and preparing the arguments for SelectCommand
 * execution
 * 
 * 
 */

public class SelectParser implements Parser {

	public SelectParser() {
	}

	// '/' forward slashes are reserved for delimiter prefixes
	private static final Pattern SELECT_TASK_DATA_FORMAT = Pattern.compile("(?:/t)\\s(?<index>\\d*)");

	private static final Pattern SELECT_EVENT_DATA_FORMAT = Pattern.compile("(?:/e)\\s(?<index>\\d*)");

	@Override
	public Command prepare(String args) {
		final Matcher taskMatcher = SELECT_TASK_DATA_FORMAT.matcher(args.trim());
		final Matcher eventMatcher = SELECT_EVENT_DATA_FORMAT.matcher(args.trim());
		if (taskMatcher.matches()) {
			int index = Integer.parseInt(taskMatcher.group("index"));
			// validation if index equals to zero
			if (index == 0) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
			} else { //index > 0
				return new SelectTaskCommand(index);
			}
			
		} else if (eventMatcher.matches()) {
			int index = Integer.parseInt(eventMatcher.group("index"));
			// validation if index equals to zero
			if (index==0){
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }else{ //index > 0
                return new SelectEventCommand(index);
            }
		} else {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}
	}

}
