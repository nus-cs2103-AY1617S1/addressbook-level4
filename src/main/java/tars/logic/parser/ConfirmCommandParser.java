package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.NoSuchElementException;

import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.IncorrectCommand;

public class ConfirmCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(priorityPrefix, tagPrefix);
        argsTokenizer.tokenize(args);

        int taskIndex;
        int dateTimeIndex;

        try {
            String indexArgs = argsTokenizer.getPreamble().get();
            String[] indexStringArray = StringUtil.indexString(indexArgs).split(EMPTY_SPACE_ONE);
            if (indexStringArray.length != 2) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ConfirmCommand.MESSAGE_USAGE));
            } else {
                taskIndex = Integer.parseInt(indexStringArray[0]);
                dateTimeIndex = Integer.parseInt(indexStringArray[1]);
            }
        } catch (IllegalValueException | NoSuchElementException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        } catch (InvalidRangeException ire) {
            return new IncorrectCommand(ire.getMessage());
        }

        try {
            return new ConfirmCommand(taskIndex, dateTimeIndex,
                    argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING),
                    argsTokenizer.getMultipleValues(tagPrefix).orElse(new HashSet<>()));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }
    }

}
