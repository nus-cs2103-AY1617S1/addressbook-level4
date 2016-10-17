package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents an activity's completion status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Completed {
    public static final String COMPLETED_ACTIVITY = "Completed";
    public static final String UNCOMPLETED_ACTIVITY = "Uncompleted";
    public boolean isCompleted;
    
    /**
     * Constructor for a Completed, takes in new boolean as status.
     */
    public Completed(boolean complete) {
        this.isCompleted = complete;
    }
    
    // Constructor for Completed, using String
    public Completed(String status) {
        assert(status != null);
        
        if (status.equals(COMPLETED_ACTIVITY)) {
            this.isCompleted = true;
        }
        else if (status.equals(UNCOMPLETED_ACTIVITY)) {
            this.isCompleted = false;
        }
    }
    
    @Override
    public String toString() {
        if (isCompleted == true) {
            return COMPLETED_ACTIVITY;
        }
        else {
            return UNCOMPLETED_ACTIVITY;
        }
    }
    
    public void complete() {
        this.isCompleted = true;
    }
}