package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

public class Priority {
    
    public static final String MESSAGE_VALUE_CONSTRAINTS = "Priority values should be between 1 to 10 inclusive";
    
    public final String priorityValue;

    /**
     * Validates given priority value.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Priority(String priorityValue) throws IllegalValueException {
        assert priorityValue != null;
        priorityValue = priorityValue.trim();
        if (!isValidValue(priorityValue)) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        this.priorityValue = priorityValue;
    }

    /**
     * Returns true if a given value is a valid priority value.
     */
    public static boolean isValidValue(String priorityValue) {
        return Integer.valueOf(priorityValue) >= 0 && Integer.valueOf(priorityValue) <= 10;
    }
    

    @Override
    public String toString() {
        return priorityValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priorityValue.equals(((Priority) other).priorityValue)); // state check
    }

    @Override
    public int hashCode() {
        return priorityValue.hashCode();
    }
}
