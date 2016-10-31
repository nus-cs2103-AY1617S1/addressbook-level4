package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0146123R
/**
 * Represents a Task's deadline
 * Empty String "" means no deadline (floating task)
 * Guarantees: is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline implements Date{
    
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline must be a valid date";
    
    private String date;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        assert date != null;
        this.date = getValidDate(date);
        assert isValidDeadline(this.date);
    }
    
    public static String getValidDate(String date) throws IllegalValueException {
        if (!isValidDeadline(date)) {
            try {
                return Date.parseDate(date);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        } else if (Date.needAddLeadingZero(date)) { 
            return Date.addLeadingZero(date);
        }
        return date;
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
    
    //@@author A0142325R
    public void updateDate(String deadline){
        this.date=deadline;
    }
    //@@author

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