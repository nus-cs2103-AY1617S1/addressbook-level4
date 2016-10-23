package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.logic.commands.AddCommand;
import seedu.taskscheduler.logic.commands.AddDeadlineCommand;
import seedu.taskscheduler.logic.commands.AddEventCommand;
import seedu.taskscheduler.logic.commands.AddFloatingCommand;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.IncorrectCommand;

public class AddCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {
        try {
            args = args.trim();
            
            Matcher matcher = EVENT_DATA_ARGS_FORMAT.matcher(args);
            // Validate arg string format
            if (matcher.matches()) {
                return new AddEventCommand(matcher.group("name"), matcher.group("startDate"),
                    matcher.group("endDate"), matcher.group("address"));
            } else if (containsDelimiters(args)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        AddCommand.MESSAGE_USAGE));
            }
    
            matcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args);
            if (matcher.matches()) {
                return new AddDeadlineCommand(matcher.group("name"), matcher.group("endDate"));
            }
            
            matcher = FLOATING_DATA_ARGS_FORMAT.matcher(args);
            if (matcher.matches()) {
                return new AddFloatingCommand(args);
            } else {   
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private boolean containsDelimiters(String args) {
        if (args.contains(START_DATE_DELIMITER) || args.contains(END_DATE_DELIMITER)) {
            return true; 
        } else {
            return false;
        }
    }
}
