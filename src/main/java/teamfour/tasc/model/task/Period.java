//@@author A0140011L
package teamfour.tasc.model.task;

import java.util.Date;
import java.util.Objects;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.CollectionUtil;
import teamfour.tasc.commons.util.ObjectUtil;

/**
 * Represents a task's period in the task list.
 * (from START_TIME to END_TIME)
 * 
 * Guarantees: immutable;
 */
public class Period {
    public static final String MESSAGE_PERIOD_CONSTRAINTS = "Start time should not be later than end time.";

    public static final String TO_STRING_FORMAT = "%s - %s";
    public static final String TO_STRING_NO_PERIOD = "No period.";
    
    private final boolean hasPeriod;
    private final Date startTime;
    private final Date endTime;
    
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
        if (hasPeriod()) {
            return String.format(TO_STRING_FORMAT, getStartTime(), getEndTime());
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
        
        return ObjectUtil.isEquivalentOrBothNull(this.hasPeriod(), otherPeriod.hasPeriod()) &&
                ObjectUtil.isEquivalentOrBothNull(this.getStartTime(), otherPeriod.getStartTime()) &&
                ObjectUtil.isEquivalentOrBothNull(this.getEndTime(), otherPeriod.getEndTime()); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getEndTime());
    }

    public boolean hasPeriod() {
        return hasPeriod;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
