package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.DeleteEventCommand;
import seedu.task.logic.commands.DeleteTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Responsible for validating and preparing the arguments for DeleteCommand execution
 * @author Tiankai
 */
public class DeleteParser implements Parser {

    public DeleteParser() {}
    
    // remember to trim 
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/t)\\s(?<index>\\d*)");
    
 // remember to trim 
    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/e)\\s(?<index>\\d*)");
    
    /**
     * Parses arguments in the context of the add person command.
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
            if (index==0) return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            
            try {
                return new DeleteTaskCommand(index);
            } catch (NumberFormatException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (eventMatcher.matches()){
            int index = Integer.parseInt(eventMatcher.group("index"));
            if (index==0) return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            
            try {
                return new DeleteEventCommand(index);
            } catch (NumberFormatException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }
    
}
