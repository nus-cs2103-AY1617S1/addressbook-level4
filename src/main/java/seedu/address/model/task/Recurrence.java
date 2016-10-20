package seedu.address.model.task;

/**
 * Represents a Task's recurrence in the task manager.
 */
public class Recurrence {
    
    public boolean value;
    public String days;
    
    public Recurrence (boolean recurring, String numOfDays) {
        if (!recurring) {
            this.days = "0";
            this.value = recurring;
        }
        else {
            this.days = numOfDays;
            this.value = recurring;
        }
    }
    
    public boolean getValue() {
        return this.value;
    }
    public String getRecurringDays() {
        return this.days;
    }

}
