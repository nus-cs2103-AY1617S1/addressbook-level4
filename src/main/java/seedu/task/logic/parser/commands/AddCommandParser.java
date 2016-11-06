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
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.TimeParser;
import seedu.task.logic.parser.TimeParserResult;
import seedu.task.logic.parser.TimeParserResult.DateTimeStatus;

public class AddCommandParser {


    private static final Pattern NATURAL_ARGS_FORMAT =
            Pattern.compile("(?<name>[^,#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_TIME =
            Pattern.compile("(?<name>[^,#]+)" + ", (at|on) (?<startTime>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_DEADLINE =
            Pattern.compile("(?<name>[^,#]+)" + ", by (?<deadline>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME =
            Pattern.compile("(?<name>[^,#]+)" + ", from (?<startTime>[^@#]+)" + " to (?<endTime>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");
    
    private static final Pattern NATURAL_ARGS_FORMAT_WITH_END_AND_START_TIME =
            Pattern.compile("(?<name>[^,#]+)" + ", to (?<endTime>[^@#]+)" + " from (?<startTime>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_DEADLINE =
            Pattern.compile("(?<name>[^,#]+)" + ", (at|on) (?<startTime>[^@#]+)" + " (by|to) (?<deadline>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME_AND_DEADLINE =
            Pattern.compile("(?<name>[^,#]+)" + ", from (?<startTime>[^@#]+)" + "to (?<endTime>[^@#]+)" + "by (?<deadline>[^@#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final String EMPTY_STRING = "";
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public static Command prepareAdd(String args) {

        final Matcher matcherNatural = NATURAL_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherStart = NATURAL_ARGS_FORMAT_WITH_START_TIME.matcher(args.trim());
        final Matcher matcherDeadline = NATURAL_ARGS_FORMAT_WITH_DEADLINE.matcher(args.trim());
        final Matcher matcherStartEnd = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME.matcher(args.trim());
        final Matcher matcherEndStart = NATURAL_ARGS_FORMAT_WITH_END_AND_START_TIME.matcher(args.trim());
        final Matcher matcherStartDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_DEADLINE.matcher(args.trim());
        final Matcher matcherStartEndDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME_AND_DEADLINE
                .matcher(args.trim());

        // Validate arg string format
        if (isNotMatch(matcherNatural, matcherStart, matcherDeadline, matcherStartEnd, matcherStartDeadline,
                matcherStartEndDeadline, matcherEndStart)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        else if (matcherNatural.matches()) {
            try {
                return new AddCommand(matcherNatural.group("name"), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                        getTagsFromArgs(matcherNatural.group("tagArguments")));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }

        } else if (matcherStart.matches() && !(Pattern.compile("at.*by").matcher(args).find())) {
            try {
                return createCommandStart(matcherStart.group("name"), matcherStart.group("startTime"), EMPTY_STRING,
                        EMPTY_STRING, getTagsFromArgs(matcherStart.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        } else if (matcherDeadline.matches()) {
            try {
                return createCommandDeadline(matcherDeadline.group("name"), EMPTY_STRING, EMPTY_STRING,
                        matcherDeadline.group("deadline"), getTagsFromArgs(matcherDeadline.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        }
        // add do hw from 3:00pm to 4:00pm by 5:00pm
        else if (matcherEndStart.matches() &&(Pattern.compile("to.*from").matcher(args).find())) {
            try {
                return createCommandStartEnd(matcherEndStart.group("name"), matcherEndStart.group("startTime"),
                        matcherEndStart.group("endTime"), EMPTY_STRING,
                        getTagsFromArgs(matcherEndStart.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if (matcherStartEnd.matches() && !(Pattern.compile("from.*to.*by").matcher(args).find())) {
            try {
                return createCommandStartEnd(matcherStartEnd.group("name"), matcherStartEnd.group("startTime"),
                        matcherStartEnd.group("endTime"), EMPTY_STRING,
                        getTagsFromArgs(matcherStartEnd.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        } else if (matcherStartDeadline.matches() && (Pattern.compile("at.*by").matcher(args).find())) {
            try {
                return createCommandStartDeadline(matcherStartDeadline.group("name"),
                        matcherStartDeadline.group("startTime"), EMPTY_STRING, matcherStartDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartDeadline.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        } else if (matcherStartEndDeadline.matches() && (Pattern.compile("from.*to.*by").matcher(args).find())) {
            try {
                return createCommandStartEndDeadline(matcherStartEndDeadline.group("name"),
                        matcherStartEndDeadline.group("startTime"), matcherStartEndDeadline.group("endTime"),
                        matcherStartEndDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartEndDeadline.group("tagArguments")));

            } catch (IllegalValueException i) {
                return new IncorrectCommand(i.getMessage());
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

    }

    private static boolean isNotMatch(final Matcher matcherNatural, final Matcher matcherStart, final Matcher matcherDeadline,
            final Matcher matcherStartEnd, final Matcher matcherStartDeadline, final Matcher matcherStartEndDeadline,
            final Matcher matcherEndStart) {
        return !matcherNatural.matches() && !matcherStart.matches() && !matcherDeadline.matches()
                && !matcherStartEnd.matches() && !matcherStartDeadline.matches()
                && !matcherStartEndDeadline.matches()&& !matcherEndStart.matches();
    }

    // @@ author A0152958R
    private static Command createCommandStart(String name, String startTime, String endTime, String deadline,
            Set<String> tags) {
        TimeParser parserTime = new TimeParser();
        TimeParserResult time = parserTime.parseTime(startTime);
        StringBuilder startString = new StringBuilder();
        if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
            buildFirstTime(time, startString);
        }
        if (startString.length() == 0) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
        try {
            return new AddCommand(name, startString.toString(), endTime, deadline, tags);
        } catch (IllegalValueException i) {
            return new IncorrectCommand(i.getMessage());
        }

    }

    // @@ author A0152958R
    private static Command createCommandStartEnd(String name, String startTime, String endTime, String deadline,
            Set<String> tags) {
        TimeParser parserTime = new TimeParser();
        String timeString = "from " + startTime + " to " + endTime;
        TimeParserResult time = parserTime.parseTime(timeString);
        if (time == null) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_INTERVAL);
        }
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();

        if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME_END_DATE_END_TIME) {
            buildFirstTime(time, start);
            buildSecondTime(time, end);
        }
        if (isEmpty(start, end)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
        try {
            return new AddCommand(name, start.toString(), end.toString(), deadline, tags);
        } catch (IllegalValueException i) {
            return new IncorrectCommand(i.getMessage());
        }
    }

    // @@ author A0152958R
    private static Command createCommandStartDeadline(String name, String startTime, String endTime, String deadline,
            Set<String> tags) {
        TimeParser parserTime = new TimeParser();
        String timeString = "from " + startTime + " to " + deadline;
        TimeParserResult time = parserTime.parseTime(timeString);
        if (time == null) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_INTERVAL);
        }
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME_END_DATE_END_TIME) {
            buildFirstTime(time, start);
            buildSecondTime(time, end);
        }
        if (isEmpty(start, end)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
        try {
            return new AddCommand(name, start.toString(), endTime, end.toString(), tags);
        } catch (IllegalValueException i) {
            return new IncorrectCommand(i.getMessage());
        }
    }

    private static boolean isEmpty(StringBuilder start, StringBuilder end) {
        return start.length() == 0 || end.length() == 0;
    }

    // @@ author A0152958R
    private static Command createCommandStartEndDeadline(String name, String startTime, String endTime, String deadline,
            Set<String> tags) {
        TimeParser parserTime = new TimeParser();
        TimeParser parserTimeDead = new TimeParser();
        TimeParser parserDeadline = new TimeParser();
        String timeString = "from " + startTime + " to " + endTime;
        String anotherTimeString = "from " + endTime + " to " + deadline;
        TimeParserResult time = parserTime.parseTime(timeString);
        TimeParserResult anotherTime = parserTimeDead.parseTime(anotherTimeString);
        TimeParserResult deadlineTime = parserDeadline.parseTime(deadline);
        if (time == null || anotherTime == null) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_INTERVAL);
        }
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        StringBuilder deadString = new StringBuilder();
        if (deadlineTime.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
            buildFirstTime(deadlineTime, deadString);
        }
        if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME_END_DATE_END_TIME) {
            buildFirstTime(time, start);
            buildSecondTime(time, end);
        }
        if (isEmpty(start, end) || deadString.length() == 0) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
        try {
            return new AddCommand(name, start.toString(), end.toString(), deadString.toString(), tags);
        } catch (IllegalValueException i) {
            return new IncorrectCommand(i.getMessage());
        }
    }

    private static void buildSecondTime(TimeParserResult time, StringBuilder end) {
        end.append(time.getSecondDate().toString());
        end.append(" ");
        end.append(time.getSecondTime().toString().substring(0, 5));
    }

    private static void buildFirstTime(TimeParserResult time, StringBuilder start) {
        start.append(time.getFirstDate().toString());
        start.append(" ");
        start.append(time.getFirstTime().toString().substring(0, 5));
    }

    private static Command createCommandDeadline(String name, String startTime, String endTime, String deadline,
            Set<String> tags) {
        TimeParser parserTime = new TimeParser();
        TimeParserResult time = parserTime.parseTime(deadline);
        StringBuilder deadlineString = new StringBuilder();
        if (time.getRawDateTimeStatus() == DateTimeStatus.START_DATE_START_TIME) {
            buildFirstTime(time, deadlineString);
        }
        if (deadlineString.length() == 0) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
        try {
            return new AddCommand(name, startTime, endTime, deadlineString.toString(), tags);
        } catch (IllegalValueException i) {
            return new IncorrectCommand(i.getMessage());
        }

    }
    // @@author

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
