package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.ListCommand;

public class ListCommandParser extends CommandParser {
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more whitespace

    /**
     * Parses arguments in the context of the list task command.
     *
     * @@author @A0140022H
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {

        if (args.isEmpty()) {
            return new ListCommand();
        }

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ListCommand(keywordSet);
    }

}
