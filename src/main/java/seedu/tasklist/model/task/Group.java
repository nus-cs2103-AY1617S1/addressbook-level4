package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {

    public static final String MESSAGE_PHONE_CONSTRAINTS = "Group description should be spaces or alphanumeric characters";
    public static final String PHONE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String description;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Group(String phone) throws IllegalValueException {
        assert phone != null;
        phone = phone.trim();
        if (!isValidGroup(phone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.description = phone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.description.equals(((Group) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
