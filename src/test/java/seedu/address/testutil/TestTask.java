package seedu.address.testutil;

import java.util.Iterator;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
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

    /**
     * A copy constructor.
     * @throws IllegalValueException 
     */
    public TestTask(TestTask taskToCopyFrom) throws IllegalValueException {
        name = new Name(taskToCopyFrom.getName().name);
        complete = new Complete(taskToCopyFrom.getComplete().isCompleted);
        deadline = new Deadline();
        period = new Period();
        deadlineRecurrence = new Recurrence();
        periodRecurrence = new Recurrence();
        
        tags = new UniqueTagList(taskToCopyFrom.tags);

        if (taskToCopyFrom.deadline.hasDeadline) {
            deadline = new Deadline(taskToCopyFrom.deadline.deadline);
        }

        if (taskToCopyFrom.period.hasPeriod) {
            period = new Period(taskToCopyFrom.period.startTime, taskToCopyFrom.period.endTime);
        }

        if (taskToCopyFrom.deadlineRecurrence.hasRecurrence) {
            deadlineRecurrence = new Recurrence(taskToCopyFrom.deadlineRecurrence.pattern,
                    taskToCopyFrom.deadlineRecurrence.frequency);
        }
        
        if (taskToCopyFrom.periodRecurrence.hasRecurrence) {
            periodRecurrence = new Recurrence(taskToCopyFrom.periodRecurrence.pattern,
                    taskToCopyFrom.periodRecurrence.frequency);
        }
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

    /**
     * Compare our old task with ourselves and see what 
     * changes were made, then add it into the command
     * 
     * @param oldTask details
     * @return the appropriate command for update
     */
    public String getUpdateCommand(int taskIndex, TestTask oldTask, Set<String> tagsToAdd,
            Set<String> tagsToRemove) {
        StringBuilder sb = new StringBuilder();
        sb.append("update " + (taskIndex + 1) + " ");
        
        if (!this.getName().equals(oldTask.getName())) {
            sb.append("name " + this.getName().name);
        }
        
        if (!this.getDeadline().equals(oldTask.getDeadline())) {
            if (this.getDeadline().hasDeadline) {
                sb.append("by " + this.getDeadline().deadline + " ");
            } else {
                sb.append("removeby ");
            }
        }
        
        if (!this.getPeriod().equals(oldTask.getPeriod())) {
            if (this.getPeriod().hasPeriod) {
                sb.append("from " + this.getPeriod().startTime + " to " + this.getPeriod().endTime + " ");
            } else {
                sb.append("removefrom ");
            }
        }

        if (!this.getDeadlineRecurrence().equals(oldTask.getDeadlineRecurrence())) {
            if (this.getDeadlineRecurrence().hasRecurrence) {
                sb.append("repeatdeadline " + this.getDeadlineRecurrence().pattern.toString() + " "
                        + this.getDeadlineRecurrence().frequency + " ");
            } else {
                sb.append("removerepeatdeadline ");
            }
        }
        
        if (!this.getPeriodRecurrence().equals(oldTask.getPeriodRecurrence())) {
            if (this.getPeriodRecurrence().hasRecurrence) {
                sb.append("repeattime " + this.getPeriodRecurrence().pattern.toString() + " "
                        + this.getPeriodRecurrence().frequency + " ");
            } else {
                sb.append("removerepeattime ");
            }
        }
        
        if (tagsToAdd != null) {
            Iterator<String> addIterator = tagsToAdd.iterator();
            while (addIterator.hasNext()) {
                sb.append("tag " + addIterator.next() + " ");
            }
        }

        if (tagsToRemove != null) {
            Iterator<String> removeIterator = tagsToRemove.iterator();
            while (removeIterator.hasNext()) {
                sb.append("removetag " + removeIterator.next() + " ");
            }
        }

        return sb.toString();
    }

}
