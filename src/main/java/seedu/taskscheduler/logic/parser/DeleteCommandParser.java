package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.DeleteCommand;
import seedu.taskscheduler.logic.commands.IncorrectCommand;

//@@author A0148145E

/**
* Parses delete command user input.
*/
public class DeleteCommandParser extends CommandParser {


    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }
}
