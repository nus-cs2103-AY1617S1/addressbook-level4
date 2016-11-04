package seedu.address.testutil;

import java.util.Calendar;
import java.util.Date;
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
 */

//@@author A0125284H

public class TestEvent extends TestActivity implements ReadOnlyEvent {

    private StartTime startTime;
    private EndTime endTime;
    
	public TestEvent() throws IllegalValueException {
		super();
	}


	public TestEvent(TestActivity testActivity) throws IllegalValueException {
		this.name = new Name(testActivity.getName().toString());
		this.reminder = new Reminder(testActivity.getReminder().toString());
		this.startTime = new StartTime("");
		this.endTime = new EndTime("");
	}


	@Override
	public StartTime getStartTime() {
        return startTime;
	}
	

	public void setStartTime(StartTime startTime) {
		this.startTime = startTime;
		
	}
	
    public void setStartTime(String newStartTime) {
        try {
			this.startTime= new StartTime(newStartTime);
		} catch (IllegalValueException e) {
			assert false;
			e.printStackTrace();
		}
    }

	@Override
	public EndTime getEndTime() {
        return endTime;
	}
	
	public void setEndTime(EndTime endTime) {
		this.endTime = endTime;
		
	}
	
    public void setEndTime(String newEndTime) {
        try {
			this.endTime= new EndTime(newEndTime);
		} catch (IllegalValueException e) {
			assert false;
			e.printStackTrace();
		}
    }
    
	@Override
	public boolean hasPassedDueDate() {
		return false;
	}
	
	@Override
    public boolean isOngoing() {
        Date now = Calendar.getInstance().getTime();
        return now.after(startTime.getCalendarValue().getTime())
                && now.before(endTime.getCalendarValue().getTime());
    }

    @Override
    public boolean isOver() {
        Date now = Calendar.getInstance().getTime();
        return now.after(endTime.getCalendarValue().getTime());
    }

	@Override
	public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } 
            return ""; 
	}
	
	@Override
    public String displayTiming() {
        return "From " + startTime.toString() + " to " + endTime.toString();
    }
	
    //methods specific to TestEvent
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        DateUtil dUtil = new DateUtil();
        String dateFormat = "EEE, MMM d, yyyy h:mm a";
        
        sb.append("add " + this.getName().fullName + " ");
        
        if (!getStartTime().value.equals(null)) {
        sb.append("s/" + dUtil.outputDateTimeAsString(this.getStartTime().getCalendarValue(), dateFormat) + " ");     
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
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Reminder: ")
                .append(getReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }



    
}
