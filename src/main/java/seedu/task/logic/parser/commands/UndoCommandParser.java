package seedu.task.logic.parser.commands;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.parser.CommandParser;

// @@author A0147335E
public class UndoCommandParser {
    /**
     * Parses arguments in the context of the undo task command.
     *
     * @param args full command args string
     * @return the undoed command
     */
    public static Command prepareUndo(String args) {

        Optional<Integer> index = CommandParser.parseIndex(args);
        if (!index.isPresent()) {
            return new UndoCommand();
        }

        return new UndoCommand(index.get());
    }
}
