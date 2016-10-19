package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;
    private int numArgs;

    public TaskBuilder() {
        this.task = new TestTask();
        numArgs = 0;
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        numArgs++;
        return this;
    }
    
    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new TaskDate(startDate));
        numArgs++;
        return this;
    }
    
    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new TaskTime(startTime));
        numArgs++;
        return this;
    }
    
    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(new TaskDate(endDate));
        numArgs++;
        return this;
    }
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TaskTime(endTime));
        numArgs++;
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        task.setNumArgs(numArgs);
        return this.task;
    }

}
