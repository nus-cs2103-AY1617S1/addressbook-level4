package tars.logic.parser;

import java.time.DateTimeException;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.commons.core.Messages;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.FreeCommand;
import tars.logic.commands.IncorrectCommand;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;


/**
 * Free command parser
 * 
 * @@author A0124333U
 *
 */
public class FreeCommandParser extends CommandParser {
    
    public static final int FIRST_DATETIME_INDEX = 0;
    public static final int SECOND_DATETIME_INDEX = 1;

    @Override
    public Command prepareCommand(String args) {
        args = args.trim();

        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        String[] dateTimeStringArray = {StringUtil.EMPTY_STRING};

        try {
            dateTimeStringArray = DateTimeUtil.parseStringToDateTime(args);
        } catch (DateTimeException dte) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        if (!dateTimeStringArray[FIRST_DATETIME_INDEX].isEmpty()) {
            return new IncorrectCommand(
                    FreeCommand.MESSAGE_DATE_RANGE_DETECTED);
        } else {
            try {
                return new FreeCommand(new DateTime(dateTimeStringArray[FIRST_DATETIME_INDEX],
                        dateTimeStringArray[SECOND_DATETIME_INDEX]));
            } catch (DateTimeException dte) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
            } catch (IllegalDateException ide) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
            }
        }
    }

}
