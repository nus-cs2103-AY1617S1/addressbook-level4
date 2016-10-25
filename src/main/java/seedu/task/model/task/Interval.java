package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's interval in the address book.
 */
public class Interval {

    public static final String MESSAGE_INTERVAL_CONSTRAINTS =
            "Interval should only contain numbers";
    public static final String INTERVAL_VALIDATION_REGEX = "[1-9][0-9]*|1";
    public static final Integer DEFAULT_VALUE= 1;
    public final Integer value;
    

    /**
     * Validates given interval.
     *
     * @throws IllegalValueException if given interval string is invalid.
     */
	public Interval(String interval) throws IllegalValueException {
		assert interval != null;
		if (interval.equals("Not Set")) {
			this.value = DEFAULT_VALUE;
		} else {
			interval = interval.trim();
			if (!isValidInterval(interval)) {
				throw new IllegalValueException(MESSAGE_INTERVAL_CONSTRAINTS);
			}
			this.value = Integer.parseInt(interval.trim());
		}
	}

    /**
     * Returns if a given string is a valid task interval.
     */
    public static boolean isValidInterval(String test) {
        return test.matches(INTERVAL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interval // instanceof handles nulls
                && this.value.equals(((Interval) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
