package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.InputMismatchException;
import java.util.regex.Matcher;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.ReplaceCommand;
import seedu.taskscheduler.logic.commands.IncorrectCommand;
import seedu.taskscheduler.model.task.Task;

//@@author A0148145E

/**
* Parses replace command user input.
*/
public class ReplaceCommandParser extends CommandParser{
    
    /**
     * Parses arguments in the context of the replace task command.
     *
     * @param matcherArgs full command args string
     * @return the prepared command
     */

    public Command prepareCommand(String args) {

        // Validate arg string format
        final Matcher indexMatcher = INDEX_COMMAND_FORMAT.matcher(args);

        if (!indexMatcher.matches()) {
            return generateReplaceCommand(args, -1);
        } else {
            int index = Integer.parseInt(indexMatcher.group("index"));
            assert index >= 0;
            String newArgs = indexMatcher.group("arguments").trim();
            return generateReplaceCommand(newArgs, index);
        }
    }

    private Command generateReplaceCommand(String args, int index) {
        try {
            Task task = generateTaskFromArgs(args);
            if (task == null) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplaceCommand.MESSAGE_USAGE));      
            } else {
                return new ReplaceCommand(index, task);
            }
        } catch (InputMismatchException ime) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReplaceCommand.MESSAGE_USAGE)); 
        } catch (IllegalValueException ex) {
            return new IncorrectCommand(ex.getMessage());
        }
    }
}

