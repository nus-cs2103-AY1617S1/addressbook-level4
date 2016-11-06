package seedu.task.logic.parser.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SortCommand;

// @@author A0147944U
public class SortCommandParser {
    
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

    /**
     * Parses arguments in the context of the sort tasks command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    public static Command prepareSort(String args) {
        if ("".equals(args)) {
            return new SortCommand("default");
        } else {
            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            final String keyword = matcher.group("keywords").toLowerCase();
            return new SortCommand(keyword);
        }
    }
}
