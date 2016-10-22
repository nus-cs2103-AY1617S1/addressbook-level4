package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's end date in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EndDate {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event end date should be written in this format 'DD-MM-YYYY'";
    public static final String DATE_VALIDATION_REGEX = "^(\\d{2}-\\d{2}-\\d{4})$";
    
    public final String endDate;
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
        endDate = endDate.trim();
        if (!endDate.equals("No End Date") && !isValidDate(endDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.endDate = endDate;
    }
    
    /**
     * Returns if a given string is a valid event date.
     */
    public static boolean isValidDate(String endDate) {
        return endDate.matches(DATE_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return endDate;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.endDate.equals(((EndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
