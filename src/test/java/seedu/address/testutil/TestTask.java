package seedu.address.testutil;

import seedu.task.model.task.*;
import seedu.task.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Description description;
    private StartDate startDate;
    private DueDate dueDate;
    private Interval interval;
    private TimeInterval timeInterval;
    private Status status;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().fullTitle + " ");
        sb.append("d/" + this.getDescription().fullDescription + " ");
        sb.append("sd/" + this.getStartDate().toString() + " ");
        sb.append("dd/" + this.getDueDate().toString() + " ");
        sb.append("i/" + this.getInterval().value + " ");
        sb.append("ti/" + this.getTimeInterval().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public Title getTitle() {
		return this.title;
	}

	@Override
	public Description getDescription() {
		return this.description;
	}

	@Override
	public StartDate getStartDate() {
		return this.startDate;
	}

	@Override
	public DueDate getDueDate() {
		return this.dueDate;
	}

	@Override
	public Interval getInterval() {
		return this.interval;
	}

	@Override
	public TimeInterval getTimeInterval() {
		return this.timeInterval;
	}
	
    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
