package seedu.address.model.todo;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Due date of a to-do
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DueDate {

    public final Date dueDate;

    /**
     * Constructor for a due date
     * Asserts title is not null
     * @throws IllegalValueException if given title is invalid
     */
    public DueDate(Date dueDate){
        assert dueDate != null;
       
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return this.dueDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && (dueDate.equals(((DueDate) other).dueDate))); // state check
    }

    @Override
    public int hashCode() {
        return dueDate.hashCode();
    }

}
