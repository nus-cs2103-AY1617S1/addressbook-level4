package seedu.address.testutil;

import java.util.ArrayList;

import seedu.menion.model.activity.*;
import seedu.menion.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyActivity {

    private ActivityName name;
    private String type;
    private Note note;
    private ActivityTime startTime;
    private ActivityDate startDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(ActivityName name) {
        this.name = name;
    }
    
    public void setActivityType(String type) {
        this.type = type;
    }

    public void setNote(Note notes) {
        this.note = notes;
    }

    public void setStartTime(ActivityTime startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(ActivityDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public ActivityName getActivityName() {
        return name;
    }
    
	@Override
	public String getActivityType() {
		return type;
	}
	
    @Override
    public ActivityDate getActivityStartDate() {
        return startDate;
    }

    @Override
    public ActivityTime getActivityStartTime() {
        return startTime;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getTaskAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getActivityName().fullName + " ");
        sb.append("by: " + this.getActivityStartDate().value + " ");
        sb.append(this.getActivityStartTime().value + " ");
        sb.append("n:" + this.getNote().value + " ");
        return sb.toString();
    }

    public String getTaskAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getActivityName())
                .append(" By: ")
                .append(getActivityStartDate())
                .append(" at: ")
                .append(getActivityStartTime())
                .append(" Note: ")
                .append(getNote())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

	@Override
	public ActivityDate getActivityEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityTime getActivityEndTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getActivityDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActivityDetails() {
		// TODO Auto-generated method stub
		
	}
}
