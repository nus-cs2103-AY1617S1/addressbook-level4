package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = "End Time should follow the format hh:mm am/pm(or h:mm am/pm)";
    public static final String TIME_VALIDATION_REGEX = "((1[012]|0[1-9]|[1-9]):[0-5][0-9](?i)(am|pm)\\sto\\s)?(1[012]|[1-9]|0[1-9]):[0-5][0-9](?i)(am|pm)";
    
    public String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public EndTime(String address) throws IllegalValueException {
        assert address != null;

        if (!isValidEndTime(address) && !address.equals("")) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);

        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid person start.
     */
    public static boolean isValidEndTime(String test) {

        return test.matches(TIME_VALIDATION_REGEX);

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
    
    public void setEndTime(String time) {
        this.value = time;
    }

}
