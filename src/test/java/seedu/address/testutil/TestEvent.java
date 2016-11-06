package seedu.address.testutil;

import java.text.SimpleDateFormat;
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


	public TestEvent(TestActivity testActivity) {
		this.name = testActivity.getName();
		this.reminder = testActivity.getReminder();
        this.tags = new UniqueTagList(testActivity.getTags());
        this.isCompleted = testActivity.getCompletionStatus();
		setStartTime("");
		setEndTime("");
	}

	public TestEvent(TestEvent testEvent) {
		this.name = testEvent.getName();
		this.reminder = testEvent.getReminder();
        this.tags = new UniqueTagList(testEvent.getTags());
        this.isCompleted = testEvent.getCompletionStatus();
		this.startTime = testEvent.getStartTime();
		this.endTime = testEvent.getEndTime();
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
	        String message = "";
	        SimpleDateFormat sdf;
	        
	        if (this.getStartTime().recurring) {
	            checkrecurring();
	            message = message.concat("Every ");
	            sdf = new SimpleDateFormat("EEEE, h:mm aa");
	        } else {
	            message = message.concat("From ");
	            sdf = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
	        }
	        
	        if (isStartAndEndOnSameDate()) {
	            SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm aa");
	            message = message.concat(sdf.format(startTime.getCalendarValue().getTime()) + " to " + timeOnly.format(endTime.getCalendarValue().getTime()));
	        } else {
	            message =  message.concat(sdf.format(startTime.getCalendarValue().getTime()) + " to " + sdf.format(endTime.getCalendarValue().getTime()));
	        }
	        return message;
	    }
	    
	    private void checkrecurring() {
	        if(this.getStartTime().value.before(Calendar.getInstance())){
	        if(this.getStartTime().RecurringMessage.contains("sun")||this.getStartTime().RecurringMessage.contains("mon")||this.getStartTime().RecurringMessage.contains("tue")||this.getStartTime().RecurringMessage.contains("wed")||this.getStartTime().RecurringMessage.contains("thu")||this.getStartTime().RecurringMessage.contains("fri")||this.getStartTime().RecurringMessage.contains("sat")){
	            this.getStartTime().value.add(Calendar.DAY_OF_WEEK, 7);
	            this.getEndTime().value.add(Calendar.DAY_OF_WEEK, 7);
	        }
	        else{
	            this.getEndTime().value.add(Calendar.DAY_OF_MONTH, 1);
	            this.getStartTime().value.add(Calendar.DAY_OF_MONTH, 1);}}
	        
	    }

	    private boolean isStartAndEndOnSameDate() {
	        return startTime.getCalendarValue().get(Calendar.YEAR) == endTime.getCalendarValue().get(Calendar.YEAR)
	                && startTime.getCalendarValue().get(Calendar.DAY_OF_YEAR) == endTime.getCalendarValue().get(Calendar.DAY_OF_YEAR);
	    }
	
    //methods specific to TestEvent
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        DateUtil dUtil = new DateUtil();
        String dateFormat = "EEE, MMM d, yyyy h:mm a";
        String recurCommand;
        sb.append("add " + this.getName().fullName + " ");
        
        if (this.getStartTime().recurring){
        	recurCommand = "every ";
        	dateFormat = "EEE HHmm";
        } else {
        	recurCommand = "";
        }	
        
        if (!getStartTime().value.equals(null)) {
        	String date = dUtil.outputDateTimeAsString(this.getStartTime().getCalendarValue(), dateFormat);
       
        	if (this.getStartTime().recurring){
        	date = date.toLowerCase();
        	}
        	
        	sb.append("s/" + recurCommand + date  + " ");     
        }
        
        if (!getEndTime().value.equals(null)) {
        	String date = dUtil.outputDateTimeAsString(this.getEndTime().getCalendarValue(), dateFormat);
       
        	if (this.getStartTime().recurring){
        	date = date.toLowerCase();
        	}
        	
        	sb.append("e/" + recurCommand + date  + " ");     
        }
        
        dateFormat = "EEE, MMM d, yyyy h:mm a";
        if (!getReminder().value.equals(null)) {
            sb.append("r/" + dUtil.outputDateTimeAsString(this.getReminder().getCalendarValue(), dateFormat) + " ");
        }
        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString().trim();
    }
    
    public String getAddCommandWithNoEndTime() {
        StringBuilder sb = new StringBuilder();
        DateUtil dUtil = new DateUtil();
        String dateFormat = "EEE, MMM d, yyyy h:mm a";
        String recurCommand;
        sb.append("add " + this.getName().fullName + " ");
        
        if (this.getStartTime().recurring){
        	recurCommand = "every ";
        	dateFormat = "EEE HHmm";
        } else {
        	recurCommand = "";
        }

        if (!getStartTime().value.equals(null)) {
        	String date = dUtil.outputDateTimeAsString(this.getStartTime().getCalendarValue(), dateFormat);
       
        	if (this.getStartTime().recurring){
        	date = date.toLowerCase();
        	}
        	
        	sb.append("s/" + recurCommand + date  + " ");     
        }
        
        dateFormat = "EEE, MMM d, yyyy h:mm a";
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
