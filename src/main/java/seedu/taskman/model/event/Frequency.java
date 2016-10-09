package seedu.taskman.model.event;

import com.google.common.base.Objects;
import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.Regex;

public class Frequency {
    // TODO: check for overflow of seconds because current validation allows years
    public static final String MESSAGE_FREQUENCY_CONSTRAINTS =
            "Task frequency should only contain frequency and unit of time in the format: " +
                    Regex.DESCRIPTION_DURATION_TYPIST_FRIENDLY;
    public static final String FREQUENCY_VALIDATION_REGEX = "^" + Regex.DURATION_TYPIST_FRIENDLY + "$";

    public final Long seconds;

    public Frequency(String frequency) throws IllegalValueException {
        assert frequency != null;
        frequency = frequency.trim();
        if (!isValidFrequency(frequency)) {
            throw new IllegalValueException(MESSAGE_FREQUENCY_CONSTRAINTS);
        }
        this.seconds = Long.valueOf(frequency);
    }

    public static boolean isValidFrequency(String test) {
    	// TODO: improve on validation, see above
        return test.matches(FREQUENCY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(seconds);
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
