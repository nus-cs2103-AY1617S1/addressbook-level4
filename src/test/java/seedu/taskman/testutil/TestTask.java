package seedu.taskman.testutil;

import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.event.*;

import java.util.Optional;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Deadline deadline;
    private Status status;
    private Frequency frequency;
    private Schedule schedule;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        status = new Status();
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

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    @Override
    public Optional<Frequency> getFrequency() {
        return Optional.ofNullable(frequency);
    }
    
    @Override
    public Optional<Schedule> getSchedule() {
        return Optional.ofNullable(schedule);
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
        sb.append("r/" + this.getFrequency().toString() + " ");
        sb.append("s/" + this.getSchedule().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
