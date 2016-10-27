package seedu.taskmanager.model.item;


import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's time in the task manager.
 * Can be startTime or endTime
 */
public class ItemTime {
    public static final String TIME_FORMAT = "HH:mm";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time input must be in HH:MM";
    public static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";

    //@@author A0140060A
    public static final String EMPTY_TIME = "";

    public final String value;

    /**
     * Convenience constructor for empty ItemTime
     */
    public ItemTime() {
        value = EMPTY_TIME;
    }
    //@@author A0065571A
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public ItemTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
    	if (test.equals(EMPTY_TIME)) {
    		return true;
    	}
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemTime // instanceof handles nulls
                && this.value.equals(((ItemTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}