package seedu.task.model.task;
//@@author A0153411W
public class Status {

    public String status;
    
    public Status(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return status;
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
