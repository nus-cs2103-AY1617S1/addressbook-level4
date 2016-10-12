package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Date should be in DD.MM.YY format";
    public static final String EMAIL_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])"
                                                        + "\\."
                                                        + "(0?[1-9]|1[012])"
                                                        + "\\."
                                                        + "\\d{2}";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        if (date == null){
            this.value = "";
            return;
        }
            
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidDate(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
