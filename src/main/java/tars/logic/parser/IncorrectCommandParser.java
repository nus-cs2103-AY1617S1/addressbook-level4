package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;

public class IncorrectCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }

}
