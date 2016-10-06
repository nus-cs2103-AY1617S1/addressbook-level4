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
    private Recurrence deadlineRecur;
    private Recurrence periodRecur;

    private UniqueTagList tags;

    public TestTask() {
        complete = new Complete(false);
        deadline = new Deadline();
        period = new Period();
        deadlineRecur = new Recurrence();
        periodRecur = new Recurrence();
        
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

    public void setDeadlineRecur(Recurrence deadlineRecur) {
        this.deadlineRecur = deadlineRecur;
    }

    public void setPeriodRecur(Recurrence periodRecur) {
        this.periodRecur = periodRecur;
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
    public Recurrence getDeadlineRecur() {
        return deadlineRecur;
    }
    
    @Override
    public Recurrence getPeriodRecur() {
        return periodRecur;
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
        sb.append("add " + this.getName().name + " ");
        
        if (this.getDeadline().hasDeadline) {
            sb.append("by " + this.getDeadline().deadline + " ");
        }

        if (this.getPeriod().hasPeriod) {
            sb.append("from " + this.getPeriod().startTime + " to " + this.getPeriod().endTime + " ");
        }

        if (this.getDeadlineRecur().hasRecurrence) {
            sb.append("repeatdeadline " + this.getDeadlineRecur().frequency + " ");
        }

        if (this.getPeriodRecur().hasRecurrence) {
            sb.append("repeatperiod " + this.getPeriodRecur().frequency + " ");
        }
        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/ " + s.tagName + " "));
        return sb.toString();
    }

}
