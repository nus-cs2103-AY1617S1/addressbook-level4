//@@author A0141052Y
package seedu.task.logic.parser;

import seedu.task.logic.commands.CompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;

public class CompleteParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        Optional<Integer> idx = parseIndex();
        
        if (!idx.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        
        return new CompleteCommand(idx.get());
    }
}
