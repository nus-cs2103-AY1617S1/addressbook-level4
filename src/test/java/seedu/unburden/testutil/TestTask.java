package seedu.unburden.testutil;

import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private TaskDescription taskD;
    private Date date;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;
    private boolean done;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskDescription getTaskDescription() {
        return taskD;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public boolean getDone() {
		return done;
	}

	@Override
	public String getDoneString() {
		if(done == true){
			return "Task Done!";
		}
		else{
			return "Task unDone!";
		}
	}
}
