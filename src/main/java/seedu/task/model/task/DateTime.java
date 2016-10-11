package seedu.task.model.task;

import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be an upcoming date";

    public final String value;

    /**
     * Validates given Date.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public DateTime(Date date) throws IllegalValueException {
        assert date != null;
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date.toString();
    }

    public DateTime (String date){
        this.value = date;
    }
    
    
    /**
     * Returns true if a given Date is valid and in future
     */
    public static boolean isValidDate(Date date) {
        return date.after(new Date());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
