package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a Task's date and time in the task book.
 */
public class Duple {

    private Date taskDate;
    private Time taskTime;

    public Duple(Date date, Time time) {
        this.taskDate = date;
        this.taskTime = time;
    }

    @Override
    public String toString() {
        return taskDate.toString() + " " + taskTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duple // instanceof handles nulls
                && this.taskDate.equals(((Duple) other).taskDate)
                && this.taskTime.equals(((Duple) other).taskTime)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDate, taskTime);
    }
    
    public Date getDate(){
        return this.taskDate;
    }

    public Time getTime(){
        return this.taskTime;
    }
}
