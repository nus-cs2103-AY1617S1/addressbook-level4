package seedu.taskman.model.event;

import org.ocpsoft.prettytime.PrettyTime;
import com.google.common.base.Objects;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.logic.parser.DateTimeParser;

import java.sql.Date;
import java.time.Instant;

public class Frequency {
    public static final String MESSAGE_FREQUENCY_CONSTRAINTS =
            "Task frequency should only contain frequency and unit of time in the format: ";
    // TODO: What is this differed thing?
    // differed: swap to multiple duration format
    public static final String FREQUENCY_VALIDATION_REGEX = "^" + DateTimeParser.SINGLE_DURATION + "$";
    public static final int MULTIPLIER_TIME_UNIX_TO_JAVA = 1000;

    public final Long seconds;
    public final PrettyTime p = new PrettyTime();

    public Frequency(String frequency) throws IllegalValueException {
        assert frequency != null;
        frequency = frequency.trim();
        if (!isValidFrequency(frequency)) {
            throw new IllegalValueException(MESSAGE_FREQUENCY_CONSTRAINTS);
        }
        this.seconds = DateTimeParser.durationToUnixTime(Instant.now().getEpochSecond(), frequency);
    }

    public Frequency(long seconds) {
        assert seconds >= 0;
        this.seconds = seconds;
    }

    public static boolean isValidFrequency(String test) {
        return test.matches(FREQUENCY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
    	// TODO: Verify if it is "exactly 1 year instead of 360 days"
        return p.format(new Date(seconds * MULTIPLIER_TIME_UNIX_TO_JAVA));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frequency frequency = (Frequency) o;
        return Objects.equal(seconds, frequency.seconds);
    }

    @Override
    public int hashCode() {
        return seconds.hashCode();
    }
}
