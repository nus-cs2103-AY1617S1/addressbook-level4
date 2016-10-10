package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminder(String)}
 */
public class Reminder {

    public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder should be in dd-mm-yy or dd-mm-yyyy format";
    public static final String REMINDER_VALIDATION_REGEX = "^[0-3][0-9]-[0-1][0-9]-[0-2][0][0-9][0-9]$";
    public static final String REMINDER_SECOND_VALIDATION_REGEX = "^[0-3][0-9]-[0-1][0-9]-[0-9][0-9]$"; 
    public final String value;

    /**
     * Validates given reminder date.
     *
     * @throws IllegalValueException if given reminder date string is invalid.
     */
    public Reminder(String reminder) throws IllegalValueException {
        assert reminder != null;
        reminder = reminder.trim();
        if (!isValidReminder(reminder)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
        }
        this.value = reminder;
    }

    /**
     * Returns if a given string is a valid task reminder date.
     */
    public static boolean isValidReminder(String test) {
        boolean result = false;
        
        if (test.matches(REMINDER_SECOND_VALIDATION_REGEX) || test.matches(REMINDER_VALIDATION_REGEX)) {
            result = true;
        }
        return result;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                && this.value.equals(((Reminder) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
