package seedu.address.logic.parser;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A parser for 12-hour clock times.
 */
public class TimeParser {

    private static final Pattern PATTERN_TIME = Pattern.compile("(?<hour>\\d{1,2})"
            + "(?:[.:](?<minute>\\d{2}))?(?<ampm>am|pm)");

    private final LocalTime referenceTime;

    public TimeParser(LocalTime referenceTime) {
        this.referenceTime = referenceTime;
    }

    public LocalTime getReferenceTime() {
        return referenceTime;
    }

    public LocalTime parse(String str) throws IllegalValueException {
        final Matcher matcher = PATTERN_TIME.matcher(str.trim());
        if (!matcher.matches()) {
            throw new IllegalValueException("invalid time format");
        }

        int hour = Integer.parseInt(matcher.group("hour"));
        if (hour > 12) {
            throw new IllegalValueException("invalid hour: " + hour);
        } else if (hour == 12) {
            hour = 0;
        }

        final int minute = matcher.group("minute") != null ? Integer.parseInt(matcher.group("minute")) : 0;
        if (minute >= 60) {
            throw new IllegalValueException("invalid minute: " + minute);
        }

        final boolean isPM = matcher.group("ampm").equals("pm");
        return LocalTime.of(hour + (isPM ? 12 : 0), minute);
    }

}
