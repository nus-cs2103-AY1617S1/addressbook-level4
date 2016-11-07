package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DeleteEventCommand;
import seedu.task.logic.commands.DeleteTaskCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * @@author A0121608N
 * Responsible for validating and preparing the arguments for DeleteCommand execution
 * 
 */
public class DeleteParser implements Parser {
    
    // remember to trim 
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/t)\\s(?<index>\\d*)");
    
    // remember to trim 
    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/e)\\s(?<index>\\d*)");
    
    /**
     * Parses arguments in the context of the DeleteCommand.
     *
     * Regex matcher will ensure that the string parsed into an integer is valid, 
     * hence there is no need to try and catch NumberFormatException
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args){
        final Matcher taskMatcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher eventMatcher = EVENT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (taskMatcher.matches()) {
            int index = Integer.parseInt(taskMatcher.group("index"));
            if (index!=0) {
                return new DeleteTaskCommand(index);
            }
        } else if (eventMatcher.matches()){
            int index = Integer.parseInt(eventMatcher.group("index"));
            if (index!=0){
                return new DeleteEventCommand(index);
            }
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
    
}
