package seedu.taskscheduler.logic.parser;

import java.util.Optional;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.MarkCommand;

//@@author A0148145E

/**
* Parses mark command user input.
*/
public class MarkCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new MarkCommand();
        }

        return new MarkCommand(index.get());
    }

}
