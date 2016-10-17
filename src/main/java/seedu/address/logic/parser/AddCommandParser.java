package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

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
            }
    
            matcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args);
    
            if (matcher.matches()) {
                return new AddDeadlineCommand(matcher.group("name"),
                    matcher.group("endDate"));
            }
            matcher = FLOATING_DATA_ARGS_FORMAT.matcher(args);
            
            
            if (matcher.matches()) {
                return new AddFloatingCommand(args);
            } else {   
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
