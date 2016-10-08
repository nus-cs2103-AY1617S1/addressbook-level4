package seedu.taskman.model.event;

import seedu.taskman.commons.exceptions.IllegalValueException;

public class Frequency {
    // TODO: write proper constrain message
    public static final String MESSAGE_FREQUENCY_CONSTRAINTS = "Placeholder for now!";
    public static final String FREQUENCY_VALIDATION_REGEX = "\\d+";

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
        // TODO: write proper validation

        return test.matches(FREQUENCY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(seconds);
    }

    @Override
    public int hashCode() {
        return seconds.hashCode();
    }
}
