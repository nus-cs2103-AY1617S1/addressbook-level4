package seedu.address.testutil;

import java.util.ArrayList;

import seedu.menion.model.activity.*;
import seedu.menion.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyActivity {

    private ActivityName name;
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

    public void setNote(Note notes) {
        this.note = notes;
    }

    public void setStartTime(ActivityTime startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(ActivityDate phone) {
        this.startDate = phone;
    }

    @Override
    public ActivityName getActivityName() {
        return name;
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
        sb.append("p/" + this.getActivityStartDate().value + " ");
        sb.append("e/" + this.getActivityStartTime().value + " ");
        sb.append("a/" + this.getNote().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
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
	public String getActivityType() {
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
