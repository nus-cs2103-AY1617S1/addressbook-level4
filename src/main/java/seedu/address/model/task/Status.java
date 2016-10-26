package seedu.address.model.task;

/**
 * Represents a task's status in the to-do-list.
 * Guarantees: immutable
 */
public class Status {

    public static enum State {
        DONE, OVERDUE, NONE, EXPIRE
    }

    public State status;

    public Status(State status) {
        assert status != null;
        this.status = status;
    }
    
    public Status(String statusStr) {
        assert statusStr != null;
        
        this.status = getStatusFromString(statusStr);
    }

    /**
     * Guarantees valid input
     * @param statusStr
     * @return respective enum State
     */
    private State getStatusFromString(String statusStr) {
        
        switch (statusStr) {
        case "DONE":
            return State.DONE;
            
        case "NONE":
            return State.NONE;
            
        case "OVERDUE":
            return State.OVERDUE;
        
        case "EXPIRE":
            return State.EXPIRE;
         
        default:
            return null; //not possible
        }
    }

    @Override
    public String toString() {
        String toReturn;
        
        switch (status) {
        case DONE:
            toReturn = "DONE";
            break;
            
        case NONE:
            toReturn = "NONE";
            break;
            
        case OVERDUE:
            toReturn = "OVERDUE";
            break;
            
        case EXPIRE:
            toReturn = "EXPIRE";
            break;
        
        default:
            toReturn = ""; //Not possible
            break;
        }
        
        return toReturn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

}
