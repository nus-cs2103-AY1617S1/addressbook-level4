package tars.logic.parser;

import tars.logic.commands.Command;
import tars.logic.commands.HelpCommand;

/**
 * Help command parser
 */
public class HelpCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new HelpCommand();
    }

}
