//@@author A0141052Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FindTagCommand;
import seedu.task.logic.commands.IncorrectCommand;

public class FindTagParser extends BaseParser {

    @Override
    public Command parse(String command, String arguments) {
        if (arguments.trim().isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }
        
        final String[] tagNames = arguments.split("\\s+");
        try {
            return new FindTagCommand(tagNames);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
