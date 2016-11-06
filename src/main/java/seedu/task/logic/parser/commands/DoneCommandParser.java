package seedu.task.logic.parser.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UndoneCommand;
import seedu.task.logic.parser.CommandParser;

// @@author A0147335E
public class DoneCommandParser {

    public static Command prepareDone(String args) {

        Optional<Integer> index = CommandParser.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }

    public static Command prepareUndone(String args) {

        Optional<Integer> index = CommandParser.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }

        return new UndoneCommand(index.get());
    }
}
