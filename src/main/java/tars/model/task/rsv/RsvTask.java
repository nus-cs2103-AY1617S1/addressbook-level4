package tars.model.task.rsv;

import java.util.Objects;
import java.util.Set;

import tars.commons.util.CollectionUtil;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Task;

/**
 * A task that has unconfirmed, reserved dates.
 * 
 * @@author A0124333U
 */

public class RsvTask extends Task {
    private Set<DateTime> dateTimeSet;
    private Set<String> tempTagSet;
    
    public RsvTask(Name name, Set<DateTime> dateTimeSet, Priority priority, Set<String> tempTagSet) {
        assert !CollectionUtil.isAnyNull(name, dateTimeSet, priority, tempTagSet);
        
        this.name = name;
        this.dateTimeSet = dateTimeSet;
        this.priority = priority;
        this.tempTagSet = tempTagSet;
    }
    
    /*
     * Accessors
     * Note: Accessor methods for the other attributes can be found in parent class, Task.java
     */

    public Set<DateTime> getDateTimeSet() {
        return dateTimeSet;
    }
    
    public Set<String> getTempTagSet() {
        return tempTagSet;
    }
    
    /*
     * Mutators
     * Note: Mutators methods for the other attributes can be found in parent class, Task.java
     */
      
    public void setDateTimeSet(Set<DateTime> dateTimeSet) {
        this.dateTimeSet = dateTimeSet;
    }
    
    public void setTempTagSet(Set<String> tempTagSet) {
        this.tempTagSet = tempTagSet;
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateTimeSet);
    }   
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" DateTime: ")
                .append(getDateTimeSet().toString())
                .append(" Priority: ")
                .append(priorityString())
                .append(" Tags: ")
                .append(getTempTagSet().toString());
        
        return builder.toString();
    }

}