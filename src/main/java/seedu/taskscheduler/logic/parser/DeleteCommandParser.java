package seedu.taskscheduler.logic.parser;

import java.util.Optional;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.DeleteCommand;

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
            return new DeleteCommand();
        }

        return new DeleteCommand(index.get());
    }
}
