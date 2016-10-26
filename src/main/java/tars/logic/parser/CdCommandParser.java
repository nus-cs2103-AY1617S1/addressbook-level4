package tars.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.logic.commands.CdCommand;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;

public class CdCommandParser extends CommandParser {
    private static final Pattern FILEPATH_ARGS_FORMAT = Pattern.compile("(?<filepath>\\S+)");

    /**
     * Parses arguments in the context of the change storage file directory (cd) command.
     * 
     * @@author A0124333U
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        final Matcher matcher = FILEPATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(CdCommand.MESSAGE_INVALID_FILEPATH));
        }

        if (!isFileTypeValid(args.trim())) {
            return new IncorrectCommand(String.format(CdCommand.MESSAGE_INVALID_FILEPATH));
        }

        return new CdCommand(args.trim());
    }

    /**
     * Checks if new file type is a valid file type
     * 
     * @@author A0124333U
     * @param args
     * @return Boolean variable of whether the file type is valid
     **/
    private Boolean isFileTypeValid(String args) {
        String filePath = args.trim();
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if (extension.equals(CdCommand.getXmlFileExt())) {
            return true;
        }
        return false;
    }

}
