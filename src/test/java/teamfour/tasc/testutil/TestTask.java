package teamfour.tasc.testutil;

import java.util.Iterator;
import java.util.Set;

import teamfour.tasc.model.task.*;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Complete complete;

    private Deadline deadline;
    private Period period;
    private Recurrence recurrence;

    private UniqueTagList tags;

    public TestTask() {
        complete = new Complete(false);
        deadline = new Deadline();
        period = new Period();
        recurrence = new Recurrence();

        tags = new UniqueTagList();
    }
    
    /**
     * A copy constructor.
     * @throws IllegalValueException 
     */
    public TestTask(ReadOnlyTask taskToCopyFrom) throws IllegalValueException {
        name = new Name(taskToCopyFrom.getName().getName());
        complete = new Complete(taskToCopyFrom.getComplete().isCompleted());
        deadline = new Deadline();
        period = new Period();
        recurrence = new Recurrence();

        tags = new UniqueTagList(taskToCopyFrom.getTags());

        if (taskToCopyFrom.getDeadline().hasDeadline()) {
            deadline = new Deadline(taskToCopyFrom.getDeadline().getDeadline());
        }

        if (taskToCopyFrom.getPeriod().hasPeriod()) {
            period = new Period(taskToCopyFrom.getPeriod().getStartTime(),
                    taskToCopyFrom.getPeriod().getEndTime());
        }

        if (taskToCopyFrom.getRecurrence().hasRecurrence()) {
            recurrence = new Recurrence(taskToCopyFrom.getRecurrence().getPattern(),
                    taskToCopyFrom.getRecurrence().getFrequency());
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

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
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
    public Recurrence getRecurrence() {
        return recurrence;
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
        sb.append("add " + "\"" + this.getName().getName() + "\" ");

        if (this.getDeadline().hasDeadline()) {
            sb.append("by " + this.getDeadline().getDeadline() + " ");
        }

        if (this.getPeriod().hasPeriod()) {
            sb.append("from " + this.getPeriod().getStartTime() + " to " + this.getPeriod().getEndTime() + " ");
        }

        if (this.getRecurrence().hasRecurrence()) {
            sb.append("repeat " + this.getRecurrence().getFrequency() + " ");
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
            sb.append("name " + this.getName().getName());
        }
        
        if (!this.getDeadline().equals(oldTask.getDeadline())) {
            if (this.getDeadline().hasDeadline()) {
                sb.append("by " + this.getDeadline().getDeadline() + " ");
            } else {
                sb.append("removeby ");
            }
        }
        
        if (!this.getPeriod().equals(oldTask.getPeriod())) {
            if (this.getPeriod().hasPeriod()) {
                sb.append("from " + this.getPeriod().getStartTime() + " to " + this.getPeriod().getEndTime() + " ");
            } else {
                sb.append("removefrom ");
            }
        }

        if (!this.getRecurrence().equals(oldTask.getRecurrence())) {
            if (this.getRecurrence().hasRecurrence()) {
                sb.append("repeat " + this.getRecurrence().getPattern().toString() + " "
                        + this.getRecurrence().getFrequency() + " ");
            } else {
                sb.append("removerepeat ");
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
