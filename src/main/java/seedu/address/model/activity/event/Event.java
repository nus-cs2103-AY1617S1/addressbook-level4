package seedu.address.model.activity.event;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

public class Event extends Activity implements ReadOnlyEvent{

    private StartTime startTime;
    private EndTime endTime;
    private boolean isOver;
    
    public Event(ReadOnlyActivity source) {
        super(source);
    }

    public Event(Name name, StartTime start, EndTime end, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);
        
        assert !CollectionUtil.isAnyNull(start, end);
        this.startTime = start;
        this.endTime = end;
        isCompleted = false;
    }
    
    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getReminder(), source.getTags());
    }
    
    @Override
    public StartTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(StartTime starttime) {
        this.startTime= starttime;
    }
    
    @Override
    public EndTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(EndTime endtime) {
        this.endTime= endtime;
    }



    @Override
    public boolean getOverStatus() {
        return isOver;
    }
    
    public void setOverStatus(boolean isOver) {
        this.isOver = isOver;
    }
    
    @Override
    public String toStringOverStatus() {
        if(isOver) {
            return "Over";
        } 
        
            return "";  
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyActivity // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyActivity) other));
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startTime, endTime, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}
