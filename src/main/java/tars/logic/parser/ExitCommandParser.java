package tars.logic.parser;

import tars.logic.commands.Command;
import tars.logic.commands.ExitCommand;

public class ExitCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ExitCommand();
    }

}
