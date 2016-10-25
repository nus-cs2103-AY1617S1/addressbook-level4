package tars.logic.parser;

import tars.logic.commands.Command;
import tars.logic.commands.UndoCommand;

public class UndoCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new UndoCommand();
    }

}
