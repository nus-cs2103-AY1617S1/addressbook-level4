package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Recurring recurring;
    private boolean isEvent;
    private boolean isDone;
    private boolean isRecurring;
    
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        isDone=false;
    }

    public void setName(Name name) {
        this.name = name;
    }


    public void setDate(Date date) {
        this.date = date;
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
    }
    
    public void setRecurringFrequency(String freq) throws IllegalValueException{
        this.isRecurring=true;
        this.recurring=new Recurring(freq);
    }
    
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }
    
    
    @Override 
    public boolean isEvent() {
        return isEvent;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override
    public boolean isDone() {
    	return isDone;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add "  +"n/"+this.getName().taskName + " ");
        if (isEvent) {
            assert date instanceof EventDate;
            EventDate eventDate = (EventDate) this.getDate();
            sb.append("s/" + eventDate.getStartDate() + " ");
            sb.append("e/" + eventDate.getEndDate() + " ");
        } else {
            assert date instanceof Deadline;
            String deadline = this.getDate().getValue();
            if (!deadline.equals("")) {
                sb.append("d/" + deadline + " ");
            }
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        if(isRecurring)
            sb.append("r/"+recurring.recurringFrequency);
        return sb.toString();
    }

	@Override
	public void markAsDone() {
		isDone=true;
		
	}
//@@LiXiaowei A0142325R
    public String getFlexiAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add ");
        if (isEvent) {
        	assert date instanceof EventDate;
        	EventDate eventDate = (EventDate) this.getDate();
        	sb.append("e/" + eventDate.getEndDate() + " ");
        	sb.append("s/" + eventDate.getStartDate() + " ");
        } else {
        	assert date instanceof Deadline;
        	String deadline = this.getDate().getValue();
        	if (!deadline.equals("")) {
        		sb.append("d/" + deadline + " ");
        	}
        }
        sb.append("n/"+this.getName().taskName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public Recurring getRecurring() {
      
        return recurring;
    }

    @Override
    public boolean isRecurring() {

        return isRecurring;
    }
    
}
