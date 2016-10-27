package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.RsvCommand;

public class RsvCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(dateTimePrefix, deletePrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.getValue(deletePrefix).isPresent()) {
            return prepareRsvDel(argsTokenizer);
        } else {
            return prepareRsvAdd(argsTokenizer);
        }
    }

    // Parses arguments for adding a reserved task
    private Command prepareRsvAdd(ArgumentTokenizer argsTokenizer) {
        if (!argsTokenizer.getValue(dateTimePrefix).isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RsvCommand.MESSAGE_DATETIME_NOTFOUND));
        }

        Set<String[]> dateTimeStringSet = new HashSet<>();

        try {
            for (String dateTimeString : argsTokenizer.getMultipleValues(dateTimePrefix).get()) {
                dateTimeStringSet.add(DateTimeUtil.getDateTimeFromArgs(dateTimeString));
            }

            return new RsvCommand(argsTokenizer.getPreamble().get(), dateTimeStringSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        } catch (NoSuchElementException nse) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        }
    }

    // Parses arguments for deleting one or more reserved tasks
    private Command prepareRsvDel(ArgumentTokenizer argsTokenizer) {
        try {
            if (argsTokenizer.getPreamble().isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RsvCommand.MESSAGE_USAGE_DEL));
            }

            String rangeIndex = StringUtil.indexString(argsTokenizer.getValue(deletePrefix).get());
            return new RsvCommand(rangeIndex);
        } catch (InvalidRangeException | IllegalValueException ie) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE_DEL));
        }
    }

}
