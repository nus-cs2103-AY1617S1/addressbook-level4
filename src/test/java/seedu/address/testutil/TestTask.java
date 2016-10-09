package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Complete complete;

    private Deadline deadline;
    private Period period;
    private Recurrence deadlineRecurrence;
    private Recurrence periodRecurrence;

    private UniqueTagList tags;

    public TestTask() {
        complete = new Complete(false);
        deadline = new Deadline();
        period = new Period();
        deadlineRecurrence = new Recurrence();
        periodRecurrence = new Recurrence();

        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setComplete(Complete complete) {
        this.complete = complete;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setDeadlineRecurrence(Recurrence deadlineRecurrence) {
        this.deadlineRecurrence = deadlineRecurrence;
    }

    public void setPeriodRecurrence(Recurrence periodRecurrence) {
        this.periodRecurrence = periodRecurrence;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Complete getComplete() {
        return complete;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Recurrence getDeadlineRecurrence() {
        return deadlineRecurrence;
    }

    @Override
    public Recurrence getPeriodRecurrence() {
        return periodRecurrence;
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
        sb.append("add " + "\"" + this.getName().name + "\" ");

        if (this.getDeadline().hasDeadline) {
            sb.append("by " + this.getDeadline().deadline + " ");
        }

        if (this.getPeriod().hasPeriod) {
            sb.append("from " + this.getPeriod().startTime + " to " + this.getPeriod().endTime + " ");
        }

        if (this.getDeadlineRecurrence().hasRecurrence) {
            sb.append("repeattime " + this.getDeadlineRecurrence().frequency + " ");
        }

        if (this.getPeriodRecurrence().hasRecurrence) {
            sb.append("repeattime " + this.getPeriodRecurrence().frequency + " ");
        }

        if (this.getTags().iterator().hasNext()){
            sb.append("tag ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append(s.tagName + " "));
        return sb.toString();
    }

}
