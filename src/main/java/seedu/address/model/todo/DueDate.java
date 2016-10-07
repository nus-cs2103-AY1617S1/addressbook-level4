package seedu.address.model.todo;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Due date of a to-do
 * Guarantees: immutable; is valid as declared in {@link #isValid(Date)}
 */
public class DueDate {

    public final Date dueDate;

    /**
     * Constructor for a due date
     * @throws IllegalValueException if given title is invalid
     */
    public DueDate(Date dueDate) throws IllegalValueException {
        if (!isValid(dueDate)) {
            throw new IllegalValueException(Messages.MESSAGE_TODO_DUEDATE_CONSTRAINTS);
        }

        this.dueDate = dueDate;
    }

    public static boolean isValid(Date dueDate) {
        return dueDate != null;
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
