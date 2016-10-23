package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.IncorrectCommand;
import seedu.taskscheduler.logic.commands.SelectCommand;

public class SelectCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }
}
