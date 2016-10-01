package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's type in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String TASK_WORD = "task";
    public static final String DEADLINE_WORD = "deadline";
    public static final String EVENT_WORD = "event";
    
    public static final String MESSAGE_TYPE_CONSTRAINTS = "Item types should only be 'task', 'deadline' or 'event'.";
    public static final String TYPE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given type.
     *
     * @throws IllegalValueException if given type string is invalid.
     */
    public Type(String type) throws IllegalValueException {
        assert type != null;
        type = type.trim();
        if (!isValidType(type)) {
            throw new IllegalValueException(MESSAGE_TYPE_CONSTRAINTS);
        }
        this.value = type;
    }

    /**
     * Returns true if a given string is a valid item type.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && this.value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
