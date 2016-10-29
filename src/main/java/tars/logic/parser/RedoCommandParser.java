package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.RedoCommand;

public class RedoCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        if (!args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand();
    }

}
