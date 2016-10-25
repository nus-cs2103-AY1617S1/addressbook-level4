//@@author A0140011L
package teamfour.tasc.model.task;

import java.util.Date;
import java.util.Objects;

import teamfour.tasc.commons.util.ObjectUtil;

/**
 * Represents a task's deadline in the task list.
 * Guarantees: immutable;
 */
public class Deadline {
    public static final String TO_STRING_NO_DEADLINE = "No deadline.";
    
    private final boolean hasDeadline;
    private final Date deadline;
    
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
        if (hasDeadline()) {
            return getDeadline().toString();
        }
        
        return TO_STRING_NO_DEADLINE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }
        
        if (other instanceof Deadline == false) { // instanceof handles nulls
            return false;
        }
        
        Deadline otherDeadline = (Deadline)other;
        
        return ObjectUtil.isEquivalentOrBothNull(this.hasDeadline(), otherDeadline.hasDeadline()) &&
                ObjectUtil.isEquivalentOrBothNull(this.getDeadline(), otherDeadline.getDeadline()); // state checkk
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeadline());
    }

    public boolean hasDeadline() {
        return hasDeadline;
    }

    public Date getDeadline() {
        return deadline;
    }
}
