package seedu.address.testutil;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.event.EndTime;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.event.StartTime;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable Event object. For testing only.
 *
 * @author Bangwu
 */

public class TestEvent extends TestActivity implements ReadOnlyEvent {

    private StartTime startTime;
    private EndTime endTime;
    
	public TestEvent() throws IllegalValueException {
		super();
	}


	@Override
	public StartTime getStartTime() {
        return startTime;
	}
	
    public void setStartTime(StartTime startTime) {
        this.startTime= startTime;
    }

	@Override
	public EndTime getEndTime() {
        return endTime;
	}
	
    public void setEndTime(EndTime endtime) {
        this.endTime= endtime;
    }
    
	@Override
	public boolean passedDueDate() {
		return false;
	}

	@Override
	public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } 
            return ""; 
	}
	
    //methods specific to TestEvent
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        DateUtil dUtil = new DateUtil();
        String dateFormat = "dd.MM.yyyy HHmm";
        
        sb.append("add " + this.getName().fullName + " ");
        
        if (!getStartTime().value.equals(null)) {
        sb.append("s/ " + dUtil.outputDateTimeAsString(this.getStartTime().getCalendarValue(), dateFormat) + " ");     
        }
        
        if (!getEndTime().value.equals(null)) {
        sb.append("e/" + dUtil.outputDateTimeAsString(this.getEndTime().getCalendarValue(), dateFormat) + " ");
        }
        
        if (!getReminder().value.equals(null)) {
            sb.append("r/" + dUtil.outputDateTimeAsString(this.getReminder().getCalendarValue(), dateFormat) + " ");
        }
        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString().trim();
    }
    

}
