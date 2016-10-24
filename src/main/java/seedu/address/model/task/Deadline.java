package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline
 * Empty String "" means no deadline (floating task)
 * Guarantees: is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline implements Date{
    
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline should follow DD.MM.YYYY[-Time(in 24 hrs)]";
    
    private String date;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        assert date != null;
        if (!isValidDeadline(date)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DATE_VALIDATION_REGEX) || test.equals("");
    }
    
    @Override
    public String getValue() {
        return date;
    }

    @Override
    public String toString() {
        return date;
    }
    
    public void updateDate(String deadline){
        this.date=deadline;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
    
}