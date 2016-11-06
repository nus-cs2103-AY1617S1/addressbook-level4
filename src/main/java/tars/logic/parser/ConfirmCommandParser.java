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

// @@author A0124333U
/**
 * Confirm command parser
 */
public class ConfirmCommandParser extends CommandParser {

    private static final int EXPECTED_INDEX_STRING_ARRAY_LENGTH = 2;
    private static final int INDEX_OF_TASK = 0;
    private static final int INDEX_OF_DATETIME = 1;

    @Override
    public Command prepareCommand(String args) {
        // there is no arguments
        if (args.trim().isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ConfirmCommand.MESSAGE_USAGE));
        }

        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(priorityPrefix, tagPrefix);
        argsTokenizer.tokenize(args);

        int taskIndex;
        int dateTimeIndex;

        try {
            String indexArgs = argsTokenizer.getPreamble().get();
            String[] indexStringArray = StringUtil.indexString(indexArgs)
                    .split(StringUtil.STRING_WHITESPACE);
            if (indexStringArray.length != EXPECTED_INDEX_STRING_ARRAY_LENGTH) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                ConfirmCommand.MESSAGE_USAGE));
            } else {
                taskIndex = Integer.parseInt(indexStringArray[INDEX_OF_TASK]);
                dateTimeIndex =
                        Integer.parseInt(indexStringArray[INDEX_OF_DATETIME]);
            }
        } catch (IllegalValueException | NoSuchElementException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ConfirmCommand.MESSAGE_USAGE));
        } catch (InvalidRangeException ire) {
            return new IncorrectCommand(ire.getMessage());
        }

        try {
            return new ConfirmCommand(taskIndex, dateTimeIndex,
                    argsTokenizer.getValue(priorityPrefix)
                            .orElse(StringUtil.EMPTY_STRING),
                    argsTokenizer.getMultipleValues(tagPrefix)
                            .orElse(new HashSet<>()));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ConfirmCommand.MESSAGE_USAGE));
        }
    }

}
