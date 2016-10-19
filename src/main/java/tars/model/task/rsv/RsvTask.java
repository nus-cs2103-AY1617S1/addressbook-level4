package tars.model.task.rsv;

import java.util.Objects;
import java.util.Set;

import tars.commons.util.CollectionUtil;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Task;

/**
 * A task that has unconfirmed, reserved dates.
 * 
 * @@author A0124333U
 */

public class RsvTask extends Task {
    
    private Name name;
    private Set<DateTime> dateTimeSet;
    
    public RsvTask(Name name, Set<DateTime> dateTimeSet) {
        assert !CollectionUtil.isAnyNull(name, dateTimeSet);
        
        this.name = name;
        this.dateTimeSet = dateTimeSet;

    }
    
    /*
     * Accessors
     */
    
    public Name getName() {
        return name;
    }
    
    public Set<DateTime> getDateTimeSet() {
        return dateTimeSet;
    }
    
    
    /*
     * Mutators
     */
      
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDateTimeSet(Set<DateTime> dateTimeSet) {
        this.dateTimeSet = dateTimeSet;
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
                .append(getDateTimeSet().toString());
        
        return builder.toString();
    }

}

