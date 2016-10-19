package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Date date;
    private boolean isEvent;
    private boolean isDone;
    
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
        sb.append("add " + this.getName().taskName + " ");
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
        return sb.toString();
    }

	@Override
	public void markAsDone() {
		isDone=true;
		
	}

}
