package seedu.task.logic.parser.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.TimeParser;
import seedu.task.logic.parser.TimeParserResult;
import seedu.task.logic.parser.TimeParserResult.DateTimeStatus;

// @@author A0152958R
public class EditCommandParser {
    
    // @@author A0147944U
    public static final Pattern EDIT_TASK_DATA_ARGS_FORMAT_NATURAL =
            Pattern.compile("(?<targetIndex>[0-9]+)" + " (?<item>[^,]+)" + ", (?<content>.+)");
    
    // @@author A0152958R
    public static Command prepareEdit(String args) {
        final Matcher matcher = EDIT_TASK_DATA_ARGS_FORMAT_NATURAL.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        int index = Integer.parseInt(matcher.group("targetIndex"));
        String item = matcher.group("item");
        String content = matcher.group("content");
        TimeParser parserTime = new TimeParser();
        TimeParserResult time = parserTime.parseTime(content);
        StringBuilder start = new StringBuilder();
        // @@author A0147944U
        switch (item) {
        case "name":
        case "n":
            try {
                return new EditCommand(index, "name", content, null);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        case "starttime":
        case "start":
        case "s":
            if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
                buildFirstTime(time, start);
            }
            if (start.length() == 0) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
            }
            try {
                return new EditCommand(index, "starttime", start.toString(), null);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        case "endtime":
        case "end":
        case "e":
            if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
                buildFirstTime(time, start);
            }
            if (start.length() == 0) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
            }
            try {
                return new EditCommand(index, "endtime", start.toString(), null);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        case "deadline":
        case "due":
        case "d":
            if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
                buildFirstTime(time, start);
            }
            if (start.length() == 0) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
            }
            try {
                return new EditCommand(index, "deadline", start.toString(), null);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        case "tag":
        case "t":
            try {
                return new EditCommand(index, "tag", item, getTagsFromArgs(" " + content));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        }
        // @@author A0152958R
    }
    
    private static void buildFirstTime(TimeParserResult time, StringBuilder start) {
        start.append(time.getFirstDate().toString());
        start.append(" ");
        start.append(time.getFirstTime().toString().substring(0, 5));
    }

    /**
     * Extracts the new task tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" #", "").split(" #"));
        return new HashSet<>(tagStrings);
    }

}