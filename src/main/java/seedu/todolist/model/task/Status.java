package seedu.todolist.model.task;

//@@author A0138601M
/**
 * Represents a Task's status in the to do list.
 * Guarantees: is one of the three values 'complete', 'incomplete' or 'overdue'
 */
public class Status {
    public enum Type {
        Incomplete("incomplete"),
        Complete("complete"),
        Overdue("overdue");
        
        String statusType;
        Type(String type) {
            statusType = type;
        }
        
        @Override
        public String toString() {
            return statusType;
        }
    }

    private Type status;

    /**
     * Constructs a Status object with the default status of type incomplete
     */
    public Status() {
        setStatus(Status.Type.Incomplete);
    }
    
    /**
     * Constructs a Status object with the specified status type
     */
    public Status(Type status) {
        setStatus(status);
    }
    
    /**
    * Constructs a Status object with the specified string name of the type
    */
    public Status(String status) {
        assert status != null;
        
        if (status.equals(Type.Incomplete.toString())) {
            setStatus(Type.Incomplete);
        } else if (status.equals(Type.Complete.toString())) {
            setStatus(Type.Complete);
        } else if (status.equals(Type.Overdue.toString())) {
            setStatus(Type.Overdue);
        } else {
            assert false : "Status must be either incomeplete, complete or overdue";
        }
    }
    
    public Type getType() {
        return this.status;
    }
    
    public void setStatus(Type status) {
        this.status = status;
    }
    
    /**
     * Returns true if the status is incomplete
     */
    public boolean isIncomplete() {
        return this.status.equals(Status.Type.Incomplete);
    }
    
    /**
     * Returns true if the status is complete
     */
    public boolean isComplete() {
        return this.status.equals(Status.Type.Complete);
    }
    
    /**
     * Returns true if the status is overdue
     */
    public boolean isOverdue() {
        return this.status.equals(Status.Type.Overdue);
    }

    @Override
    public String toString() {
        return status.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status == ((Status) other).status); // state check
    }

}
