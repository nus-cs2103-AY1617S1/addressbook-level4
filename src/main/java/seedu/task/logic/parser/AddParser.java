package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Responsible for validating and preparing the arguments for AddCommand execution
 * @author kian ming
 */

public class AddParser implements Parser {

    public AddParser() {}
    
    // remember to trim 
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?: /desc (?<description>[^/]+))*"
                    + "(?: /by (?<deadline>[^/]+))*$"
                    );
    
 // remember to trim 
    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?: /desc (?<description>[^/]+))*"
                    + "(?: /from (?<duration>[^/]+))*$"
                    );
    
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
            try {
                return new AddTaskCommand(
                        taskMatcher.group("name").trim(),
                        taskMatcher.group("description").trim()
    //                    ,matcher.group("deadline")
                );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (eventMatcher.matches()){
            try {
                return new AddEventCommand(
                        eventMatcher.group("name").trim(),
                        eventMatcher.group("description").trim(),
                        eventMatcher.group("duration").trim()
                );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
    
}
