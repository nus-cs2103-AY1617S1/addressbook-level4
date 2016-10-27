package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.TagCommand;

public class TagCommandParser extends CommandParser {
    private static final Pattern TAG_EDIT_COMMAND_FORMAT = Pattern.compile("\\d+ \\w+$");

    /**
     * Parses arguments in the context of the tag command.
     * 
     * @@author A0139924W
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(listPrefix, editPrefix, deletePrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.getValue(listPrefix).isPresent()) {
            return new TagCommand(listPrefix);
        }

        if (argsTokenizer.getValue(editPrefix).isPresent()) {
            String editArgs = argsTokenizer.getValue(editPrefix).get();
            final Matcher matcher = TAG_EDIT_COMMAND_FORMAT.matcher(editArgs);
            if (matcher.matches()) {
                return new TagCommand(editPrefix, editArgs.split(EMPTY_SPACE_ONE));
            }
        }

        if (argsTokenizer.getValue(deletePrefix).isPresent()) {
            String index = argsTokenizer.getValue(deletePrefix).get();
            return new TagCommand(deletePrefix, index);
        }

        return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

}
