//@@author A0153467Y
package seedu.task.logic.parser;

import seedu.task.logic.commands.UncompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;

public class UncompleteParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        Optional<Integer> idx = parseIndex();
        
        if (!idx.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
        }
        
        return new UncompleteCommand(idx.get());
    }
}
