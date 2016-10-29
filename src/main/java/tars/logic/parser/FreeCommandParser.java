package tars.logic.parser;

import java.time.DateTimeException;
import java.util.regex.Pattern;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.commons.core.Messages;
import tars.commons.util.DateTimeUtil;
import tars.logic.commands.Command;
import tars.logic.commands.FreeCommand;
import tars.logic.commands.IncorrectCommand;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;

//@@author A0124333U

public class FreeCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        args = args.trim();

        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        String[] dateTimeStringArray = { "" };

        try {
            dateTimeStringArray = DateTimeUtil.getDateTimeFromArgs(args);
        } catch (DateTimeException dte) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        if (!dateTimeStringArray[0].isEmpty()) {
            return new IncorrectCommand(FreeCommand.MESSAGE_DATE_RANGE_DETECTED);
        } else {
            try {
                return new FreeCommand(new DateTime(dateTimeStringArray[0], dateTimeStringArray[1]));
            } catch (DateTimeException dte) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
            } catch (IllegalDateException ide) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
            }
        }
    }

}
