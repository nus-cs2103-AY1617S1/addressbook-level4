//@@author A0139164A
package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents an activity's completion status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Completed {
    public static final String COMPLETED_ACTIVITY = "Completed";
    public static final String UNCOMPLETED_ACTIVITY = "Uncompleted";
    public boolean status;
    
    /**
     * Constructor for a Completed, takes in new boolean as status.
     */
    public Completed(boolean complete) {
        this.status = complete;
    }
    
    // Constructor for Completed, using String
    public Completed(String status) {
        assert(status != null);
        
        if (status.equals(COMPLETED_ACTIVITY)) {
            this.status = true;
        }
        else if (status.equals(UNCOMPLETED_ACTIVITY)) {
            this.status = false;
        }
    }
    
    @Override
    public String toString() {
        if (status) {
            return COMPLETED_ACTIVITY;
        }
        else {
            return UNCOMPLETED_ACTIVITY;
        }
    }
    
    public void complete() {
        this.status = true;
    }
}