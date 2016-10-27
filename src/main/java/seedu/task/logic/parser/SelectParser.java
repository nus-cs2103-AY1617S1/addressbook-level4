package seedu.task.logic.parser;

/**
 * Parses arguments in the context of the add task command.
 *
 * @param args full command args string
 * @return the prepared command
 * @author Yee Heng
 */

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.SelectEventCommand;
import seedu.task.logic.commands.SelectTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Responsible for validating and preparing the arguments for SelectCommand
 * execution
 * //@@author A0125534L
*/
//@@author A0125534L
public class SelectParser implements Parser {

    public SelectParser() {}
    
     
    private static final Pattern SELECT_TASK_DATA_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/t)\\s(?<index>\\d*)");
   
    private static final Pattern SELECT_EVENT_DATA_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:/e)\\s(?<index>\\d*)");
    
    
    @Override
    public Command prepare(String args){
        final Matcher taskMatcher = SELECT_TASK_DATA_FORMAT.matcher(args.trim());
        final Matcher eventMatcher = SELECT_EVENT_DATA_FORMAT.matcher(args.trim());
        if (taskMatcher.matches()) {
            int index = Integer.parseInt(taskMatcher.group("index"));
            try {
                return new SelectTaskCommand(index);
            } catch (NumberFormatException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (eventMatcher.matches()){
            int index = Integer.parseInt(eventMatcher.group("index"));
            try {
                return new SelectEventCommand(index);
            } catch (NumberFormatException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
    
}
