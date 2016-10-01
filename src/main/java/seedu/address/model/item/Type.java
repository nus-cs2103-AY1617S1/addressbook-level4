package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Type {

    public static final String TASK_WORD = "task";
    public static final String DEADLINE_WORD = "deadline";
    public static final String EVENT_WORD = "event";
    
    public static final String MESSAGE_PHONE_CONSTRAINTS = "Item phone numbers should only contain numbers";
    public static final String PHONE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Type(String phone) throws IllegalValueException {
        assert phone != null;
        phone = phone.trim();
        if (!isValidPhone(phone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = phone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
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
