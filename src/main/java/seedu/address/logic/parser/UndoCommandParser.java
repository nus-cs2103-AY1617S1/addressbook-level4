package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UndoCommand;

public class UndoCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new UndoCommand();
    }

}
