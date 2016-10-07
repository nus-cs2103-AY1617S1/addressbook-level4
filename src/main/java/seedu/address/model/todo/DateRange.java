package seedu.address.model.todo;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents the Date Range of a to-do
 * Guarantees: immutable; is valid as declared in {@link #isValid(Date, Date)}
 */
public class DateRange {

    public final Date startDate, endDate;

    /**
     * Constructor for a date range
     * @throws IllegalValueException if given title is invalid
     */
    public DateRange(Date startDate, Date endDate) throws IllegalValueException {
        if (!isValid(startDate, endDate)) {
            throw new IllegalValueException(Messages.MESSAGE_TODO_DATERANGE_CONSTRAINTS);
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public static boolean isValid(Date startDate, Date endDate) {
        if (CollectionUtil.isAnyNull(startDate, endDate)) {
            return false;
        }

        if (endDate.compareTo(startDate) >= 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return this.startDate.toString() + " " + this.endDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateRange // instanceof handles nulls
                && (startDate.equals(((DateRange) other).startDate) 
                &&  endDate.equals(((DateRange) other).endDate))); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode() + endDate.hashCode();
    }

}
