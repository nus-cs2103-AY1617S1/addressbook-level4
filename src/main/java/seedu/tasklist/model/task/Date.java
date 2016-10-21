package seedu.tasklist.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task list. Guarantees: immutable; is valid as
 * declared in {@link #isValidDate(String)}
 */
public class Date {

    private static final String MESSAGE_DATE_CONSTRAINTS = "Invalid Date format\nDate format: DDMMYYYY";
    private static final String DATE_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";

    private final LocalDate date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

        if (!date.isEmpty() && date.length() == 8) {
            this.date = LocalDate.of(Integer.parseInt(date.substring(4, 8)), Integer.parseInt(date.substring(2, 4)),
                    Integer.parseInt(date.substring(0, 2)));
        } else {
            this.date = null;
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        if (date != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return df.format(date);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.date.equals(((Date) other).date)); // state
                                                                   // check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
