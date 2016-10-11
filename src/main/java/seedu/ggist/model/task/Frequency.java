package seedu.ggist.model.task;


import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task' recurring in the GGist.
 * Guarantees: immutable; is valid as declared in {daily|weekly|monthly|yearly|fortnightly}
 */
public class Frequency {

    public static final String MESSAGE_FREQUENCY_CONSTRAINTS =
            "Task frequency should be daily, fortnigntly,weekly,monthly or yearly";
    public static final String FREQUENCY_VALIDATION_REGEX = "daily|fortnightly|weekly|monthly|yearly";

    public final String value;

    /**
     * Validates given frequency.
     *
     * @throws IllegalValueException if given frequency string is invalid.
     */
    public Frequency(String frequency) throws IllegalValueException {
        assert frequency != null;
        frequency = frequency.trim();
        if (!isValidEmail(frequency)) {
            throw new IllegalValueException(MESSAGE_FREQUENCY_CONSTRAINTS);
        }
        this.value = frequency;
    }

    /**
     * Returns if a given string is a valid frequency.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(FREQUENCY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Frequency // instance of handles nulls
                && this.value.equals(((Frequency) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
