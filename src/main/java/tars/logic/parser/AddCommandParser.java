package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.util.HashSet;
import java.util.NoSuchElementException;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.ExtractorUtil;
import tars.logic.commands.AddCommand;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;

public class AddCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the add task command.
     *
     * @@author A0139924W
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(tagPrefix, priorityPrefix, dateTimePrefix, recurringPrefix);
        argsTokenizer.tokenize(args);

        try {
            return new AddCommand(argsTokenizer.getPreamble().get(),
                    DateTimeUtil.getDateTimeFromArgs(
                            argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING)),
                    argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING),
                    argsTokenizer.getMultipleValues(tagPrefix).orElse(new HashSet<String>()),
                    ExtractorUtil.getRecurringFromArgs(
                            argsTokenizer.getValue(recurringPrefix).orElse(EMPTY_STRING),
                            recurringPrefix));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        } catch (NoSuchElementException nse) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

}
