package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;

public class ClearCommandParser extends CommandParser{

    @Override
    public Command prepareCommand(String args) {
        return new ClearCommand();
    }
}
