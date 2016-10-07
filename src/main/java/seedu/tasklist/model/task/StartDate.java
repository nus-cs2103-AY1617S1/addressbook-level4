package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_PHONE_CONSTRAINTS = "StartDate startDate should be spaces or alphanumeric characters";
    public static final String PHONE_VALIDATION_REGEX = "\\d+";

    public final String startDate;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public StartDate(String phone) throws IllegalValueException {
        assert phone != null;
        phone = phone.trim();
        if (!isValidStartDate(phone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.startDate = phone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

}
