package tars.logic.parser;

import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;

public class ClearCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ClearCommand();
    }

}
