//@@author A0153467Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UnpinCommand;

public class UnpinParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        
        Optional<Integer> index = parseIndex();
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
        }
        return new UnpinCommand(index.get());
    }
}
