package seedu.taskman.model.event;

import com.google.common.base.Objects;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.logic.parser.DateTimeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schedule {
    // UG/DG: specify new datetime format
    // todo: indicate in example that format: "month-date-year time". there MUST be a space before time, not colon
	public static final String MESSAGE_SCHEDULE_CONSTRAINTS =
            "Task schedule should only contain dates and times in the format: " +
                    // DATETIME to DATETIME
                    DateTimeParser.DESCRIPTION_DATE_TIME_SHORT + " (a \",\" or \"to\") " +
                    DateTimeParser.DESCRIPTION_DATE_TIME_SHORT +
                    // DATETIME for DURATION
                    "\nOr the format:\n" + DateTimeParser.DESCRIPTION_DATE_TIME_SHORT + " for " +
                    DateTimeParser.DESCRIPTION_DURATION +
                    "\nDATETIME: " + DateTimeParser.DESCRIPTION_DATE_TIME_FULL;

    public static final String ERROR_NEGATIVE_DURATION = "Duration is negative!";
    public static final String ERROR_BAD_START_DATETIME = "Bad start datetime";
    public static final String ERROR_BAD_END_DATETIME = "Bad end datetime";

    public static final String SCHEDULE_DIVIDER_GROUP = "((?:, )|(?: to )|(?: for ))";
	public static final String SCHEDULE_VALIDATION_REGEX =
            "(.*)" + SCHEDULE_DIVIDER_GROUP + "(.*)";

    public final long startEpochSecond;
    public final long endEpochSecond;

    public Schedule(long startEpochSecond, long endEpochSecond) throws IllegalValueException {

        boolean endIsBeforeStart = (endEpochSecond - startEpochSecond) < 0;
        if (startEpochSecond <= 0 || endEpochSecond <= 0 || endIsBeforeStart) {
            throw new IllegalValueException(ERROR_NEGATIVE_DURATION);
        }
        this.startEpochSecond = startEpochSecond;
        this.endEpochSecond = endEpochSecond;
    }

    public Schedule(String schedule) throws IllegalValueException {
        schedule = schedule.trim();
        Pattern pattern = Pattern.compile(SCHEDULE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(schedule);
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_CONSTRAINTS);
        } else {
            String start = matcher.group(1).trim();
            String divider = matcher.group(2).trim();
            boolean endingIsDuration = divider.contains("for");

            try {
                startEpochSecond = DateTimeParser.getUnixTime(start);
            } catch (DateTimeParser.IllegalDateTimeException e) {
                throw new IllegalValueException(
                        MESSAGE_SCHEDULE_CONSTRAINTS + "\n" +
                        ERROR_BAD_START_DATETIME + ", '" + start + "'");
            }

            if (endingIsDuration) {
                String duration = matcher.group(3).trim();
                endEpochSecond = DateTimeParser.durationToUnixTime(startEpochSecond, duration);
            } else {
                String end = matcher.group(3).trim();
                endEpochSecond = DateTimeParser.getUnixTime(end, ERROR_BAD_END_DATETIME);
            }
        }

        if (endEpochSecond < startEpochSecond) {
            throw new IllegalValueException(ERROR_NEGATIVE_DURATION);
        }
    }
    
    public static boolean isValidSchedule(String test) {
        try {
            new Schedule(test);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return DateTimeParser.epochSecondToShortDateTime(startEpochSecond) +
                " to\n" +
                DateTimeParser.epochSecondToDetailedDateTime(endEpochSecond);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return startEpochSecond == schedule.startEpochSecond &&
                endEpochSecond == schedule.endEpochSecond;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startEpochSecond, endEpochSecond);
    }

}
