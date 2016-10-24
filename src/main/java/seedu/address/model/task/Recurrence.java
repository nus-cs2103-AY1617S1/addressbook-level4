package seedu.address.model.task;

/**
 * Represents a Task's recurrence in the task manager.
 */
public class Recurrence {
    
    public boolean value;
    public String days;
    
    public Recurrence (String numOfDays) {
        if (numOfDays.equals("")) {
            this.days = "";
            this.value = false;
        }
        else {
            this.days = numOfDays;
            this.value = true;
        }
    }
    
    public boolean getValue() {
        return this.value;
    }
    public String getRecurFreq() {
        return this.days;
    }

    public void setRecurFreq(String days) {
        this.days = days;
    }
    @Override
    public String toString() {
        return days;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && this.days.equals(((Recurrence) other).days)); // state check
    }
    @Override
    public int hashCode() {
        return days.hashCode();
    }
}
