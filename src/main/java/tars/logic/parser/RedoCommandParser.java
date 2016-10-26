package tars.logic.parser;

import tars.logic.commands.Command;
import tars.logic.commands.RedoCommand;

public class RedoCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new RedoCommand();
    }

}
