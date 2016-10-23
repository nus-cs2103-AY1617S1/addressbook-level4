package seedu.taskscheduler.model.task;


import seedu.taskscheduler.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's address in the Task Scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Location {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task addresses can be in any format";
    public static final String ADDRESS_VALIDATION_REGEX = ".*";

    public final String value;

    public Location() {
        value = "";
    }
    /**
     * Validates given task address.
     *
     * @throws IllegalValueException if given task address string is invalid.
     */
    public Location(String address) throws IllegalValueException {
        assert address != null;
        if (!isValidAddress(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = address.trim();
    }

    /**
     * Returns true if a given string is a valid task address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}