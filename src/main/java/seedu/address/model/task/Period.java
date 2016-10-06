package seedu.address.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ObjectUtil;

/**
 * Represents a task's period in the task list.
 * (from START_TIME to END_TIME)
 * 
 * Guarantees: immutable;
 */
public class Period {
    public static final String MESSAGE_PERIOD_CONSTRAINTS = "Start time should not be later than end time.";

    public static final String TO_STRING_FORMAT = "%c - %c";
    public static final String TO_STRING_NO_PERIOD = "No period.";
    
    public final boolean hasPeriod;
    public final Date startTime;
    public final Date endTime;
    
    /**
     * Constructor for no period given for task.
     */
    public Period() {
        this.hasPeriod = false;
        this.startTime = null;
        this.endTime = null;
    }
    
    /**
     * Constructor for a given period.
     * 
     * Timings should not be null and
     * period must be valid (see {@link #isValidPeriod(Date, Date)}).
     */
    public Period(Date startTime, Date endTime) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(startTime, endTime);
        
        if (!isValidPeriod(startTime, endTime)) {
            throw new IllegalValueException(MESSAGE_PERIOD_CONSTRAINTS);
        }
        
        this.hasPeriod = true;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns true if given dates are not in the wrong order.
     * (i.e. Start Time < End Time)
     */
    private boolean isValidPeriod(Date startTime, Date endTime) {
        return startTime.before(endTime);
    }

    @Override
    public String toString() {
        if (hasPeriod) {
            return String.format(TO_STRING_FORMAT, startTime, endTime);
        }
        
        return TO_STRING_NO_PERIOD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }
        
        if (other instanceof Period == false) { // instanceof handles nulls
            return false;
        }
        
        Period otherPeriod = (Period)other;
        
        return ObjectUtil.isEquivalentOrBothNull(this.hasPeriod, otherPeriod.hasPeriod) &&
                ObjectUtil.isEquivalentOrBothNull(this.startTime, otherPeriod.startTime) &&
                ObjectUtil.isEquivalentOrBothNull(this.endTime, otherPeriod.endTime); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
