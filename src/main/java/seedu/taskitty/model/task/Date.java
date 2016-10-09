package seedu.taskitty.model.task;

import seedu.taskitty.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task dates should be in the format dd/mm/yyyy or ddmmyyyy";
    public static final String DATE_VALIDATION_REGEX_FORMAT_1 =
            "[\\p{Digit}]{1,2}/[\\p{Digit}]{1,2}/[\\p{Digit}]{4}";
    public static final String DATE_VALIDATION_REGEX_FORMAT_2 = "[\\p{Digit}]{8}";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Date(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(DATE_VALIDATION_REGEX_FORMAT_1) ||
                test.matches(DATE_VALIDATION_REGEX_FORMAT_2);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.fullName.equals(((Date) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
