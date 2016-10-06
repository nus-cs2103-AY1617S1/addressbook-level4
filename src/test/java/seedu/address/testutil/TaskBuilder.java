package seedu.address.testutil;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

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

    public TaskBuilder withDeadlineRecur(Recurrence.Pattern pattern, int frequency)
            throws IllegalValueException {
        this.task.setDeadlineRecur(new Recurrence(pattern, frequency));
        return this;
    }

    public TaskBuilder withPeriodRecur(Recurrence.Pattern pattern, int frequency)
            throws IllegalValueException {
        this.task.setPeriodRecur(new Recurrence(pattern, frequency));
        return this;
    }

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
