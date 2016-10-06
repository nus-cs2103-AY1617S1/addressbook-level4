package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.util.ObjectUtil;

/**
 * Represents a task's deadline in the task list.
 * Guarantees: immutable;
 */
public class Deadline {
    public static final String TO_STRING_NO_DEADLINE = "No deadline";
    
    public final boolean hasDeadline;
    public final Date deadline;
    
    /**
     * Constructor for no deadline.
     */
    public Deadline() {
        this.hasDeadline = false;
        this.deadline = null;
    }
    
    /**
     * Constructor for a given deadline.
     * 
     * Deadline should not be null.
     */
    public Deadline(Date deadline) {
        assert deadline != null;
        
        this.hasDeadline = true;
        this.deadline = deadline;
    }
    
    @Override
    public String toString() {
        if (hasDeadline) {
            return deadline.toString();
        }
        
        return TO_STRING_NO_DEADLINE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && ObjectUtil.isEquivalentOrBothNull(this.deadline, 
                                                     ((Deadline) other).deadline)); // state check
    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }
}
