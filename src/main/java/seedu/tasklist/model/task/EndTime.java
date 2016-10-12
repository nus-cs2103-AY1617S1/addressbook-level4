package seedu.tasklist.model.task;


import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End time should only be entered in 24 hrs format or 12 hrs format.";
    public static final String END_TIME_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        //assert email != null;
        if (!isValidEndTime(endTime)) {
            throw new IllegalValueException(MESSAGE_END_TIME_CONSTRAINTS);
        }

        this.value = endTime;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEndTime(String test) {
    	if(test==null){
    		return true;
    	}
        return test.matches(END_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
