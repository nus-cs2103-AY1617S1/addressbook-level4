package seedu.taskscheduler.logic.parser;

import java.util.Optional;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.UnmarkCommand;

//@@author A0138696L

/**
* Parses unmark command user input.
*/
public class UnmarkCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the unmark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new UnmarkCommand();
        }
        return new UnmarkCommand(index.get());
    }

}

