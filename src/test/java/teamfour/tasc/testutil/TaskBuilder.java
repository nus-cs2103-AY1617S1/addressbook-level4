package teamfour.tasc.testutil;

import java.util.Date;

import teamfour.tasc.model.task.*;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.Recurrence;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    //@@author A0140011L
    public TaskBuilder withCompleteStatus(boolean complete) throws IllegalValueException {
        this.task.setComplete(new Complete(complete));
        return this;
    }
    
    public TaskBuilder withDeadline(Date deadline) throws IllegalValueException {
        this.task.setDeadline(new Deadline(deadline));
        return this;
    }
    
    public TaskBuilder withPeriod(Date start, Date end) throws IllegalValueException {
        this.task.setPeriod(new Period(start, end));
        return this;
    }

    public TaskBuilder withRecurrence(Recurrence.Pattern pattern, int frequency)
            throws IllegalValueException {
        this.task.setRecurrence(new Recurrence(pattern, frequency));
        return this;
    }
    
    //@@author
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
