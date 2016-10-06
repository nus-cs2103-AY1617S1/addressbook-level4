package seedu.taskman.testutil;

import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements EventInterface {

    private Title title;
    private Deadline deadline;
    private Status status;
    private Recurrence recurrence;
    private Schedule schedule;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Recurrence getRecurrence() {
        return recurrence;
    }
    
    @Override
    public Schedule getSchedule() {
        return schedule;
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
        sb.append("add " + this.getTitle().title + " ");
        sb.append("d/" + this.getDeadline().toString() + " ");
        sb.append("c/" + this.getStatus().toString() + " ");
        sb.append("r/" + this.getRecurrence().toString() + " ");
        sb.append("s/" + this.getSchedule().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public boolean isRecurring() {
		return recurrence == null;
	}

	@Override
	public boolean isScheduled() {
		return schedule == null;
	}
}
