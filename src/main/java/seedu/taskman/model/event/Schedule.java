package seedu.taskman.model.event;

import com.google.common.base.Objects;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.logic.parser.DateTimeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schedule {
    // UG/DG: specify datetime format
    // todo: indicate in example that format: "month-date-year time". there MUST be a space before time, not colon
	public static final String MESSAGE_SCHEDULE_CONSTRAINTS =
            "Task schedule should only contain dates and times in the format: " +
                    // DATETIME to DATETIME
                    DateTimeParser.DESCRIPTION_DATE_TIME + " (a \",\" or \"to\") " +
                    DateTimeParser.DESCRIPTION_DATE_TIME +
                    // DATETIME for MULTIPLE_DURATION
                    " \nOr the format: " + DateTimeParser.DESCRIPTION_DATE_TIME + " for "
                    + DateTimeParser.DESCRIPTION_DURATION;

    public static final String SCHEDULE_DIVIDER_GROUP = "((?:, )|(?: to )|(?: for ))";
	public static final String SCHEDULE_VALIDATION_REGEX =
            "(.*)" + SCHEDULE_DIVIDER_GROUP + "(.*)";

    // unix time
    public final long startUnixTime;
    public final long endUnixTime;

    public Schedule(String schedule) throws IllegalValueException {
        schedule = schedule.trim();
        Pattern pattern = Pattern.compile(SCHEDULE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(schedule);
        if (!matcher.matches()) {
            throw new IllegalValueException("Bad schedule input");
        } else {
            String start = matcher.group(0).trim();
            String divider = matcher.group(1).trim();
            boolean endingIsDuration = divider.contains("for");

            startUnixTime = DateTimeParser.getUnixTime(start);

            if (endingIsDuration) {
                String duration = matcher.group(2).trim();
                endUnixTime = DateTimeParser.durationToUnixTime(startUnixTime, duration);
            } else {
                String end = matcher.group(2).trim();
                endUnixTime = DateTimeParser.getUnixTime(end);
            }
        }

        if (endUnixTime < startUnixTime) {
            throw new IllegalValueException("End time is before start time");
        }
    }
    
    /**
     * Returns true if a given string is a valid schedule.
     */
    public static boolean isValidSchedule(String test) {
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startUnixTime + " to " + endUnixTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equal(
                startUnixTime == schedule.startUnixTime,
                endUnixTime == schedule.endUnixTime
        );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startUnixTime, endUnixTime);
    }

}
