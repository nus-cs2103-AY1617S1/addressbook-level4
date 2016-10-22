package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.EditCommand;
import seedu.taskscheduler.logic.commands.EditDeadlineCommand;
import seedu.taskscheduler.logic.commands.EditEventCommand;
import seedu.taskscheduler.logic.commands.EditFloatingCommand;
import seedu.taskscheduler.logic.commands.IncorrectCommand;

public class EditCommandParser extends CommandParser{
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    
    public Command prepareCommand(String args) {
        try {
            args = args.trim();
            final Matcher matcher = INDEX_COMMAND_FORMAT.matcher(args);
    
            // Validate arg string format
            if (!matcher.matches()) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            int index = Integer.parseInt(matcher.group("index"));
            assert index >= 0;
            args = matcher.group("arguments").trim();
            Matcher editMatcher = EVENT_DATA_ARGS_FORMAT.matcher(args);
            
            if (editMatcher.matches()) {
                return new EditEventCommand(index, editMatcher.group("name"), editMatcher.group("startDate"),
                        editMatcher.group("endDate"), editMatcher.group("address"));
            }
            editMatcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args);
            
            if (editMatcher.matches()) {
                return new EditDeadlineCommand(index, editMatcher.group("name"),
                        editMatcher.group("endDate")); 
            }
            
            editMatcher = FLOATING_DATA_ARGS_FORMAT.matcher(args);
            if (editMatcher.matches()) {
                return new EditFloatingCommand(index, args);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));      
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
