package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.UdCommand;

public class UdCommandParser extends CommandParser {

    private static final String INVALID_RANGE = "Start index should be before end index.";
    
    /**
     * Parses arguments in the context of the ud command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        args = args.trim();

        if (EMPTY_STRING.equals(args)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UdCommand.MESSAGE_USAGE));
        }

        try {
            String rangeIndex = StringUtil.indexString(args);
            args = rangeIndex;
        } catch (InvalidRangeException ire) {
            return new IncorrectCommand(
                    String.format(INVALID_RANGE + "\n" + UdCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UdCommand.MESSAGE_USAGE));
        }

        return new UdCommand(args);
    }

}
