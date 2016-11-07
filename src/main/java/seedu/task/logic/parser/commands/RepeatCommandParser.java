package seedu.task.logic.parser.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.RepeatCommand;

// @@author A0147944U
public class RepeatCommandParser {

    private static final Pattern REPEAT_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[0-9]+)" + " (?<interval>[^,]+)");
    
    private static final Pattern REPEAT_ARGS_FORMAT_FOR_FLOATING_TASK = Pattern.compile("(?<targetIndex>[0-9]+)");

    /**
     * Parses arguments in the context of the repeat task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    public static Command prepareRepeat(String args) {
        final Matcher matcher = REPEAT_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherForFloating = REPEAT_ARGS_FORMAT_FOR_FLOATING_TASK.matcher(args.trim());
        if (matcherForFloating.matches()) {
            int index = Integer.parseInt(matcherForFloating.group("targetIndex"));
            return new RepeatCommand(index, "");
        } else if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepeatCommand.MESSAGE_USAGE));
        }
        int index = Integer.parseInt(matcher.group("targetIndex"));
        String interval = matcher.group("interval");
        return new RepeatCommand(index, interval);
    }
}
