package seedu.ggist.testutil;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setTaskName(new TaskName(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setStartDate(new TaskDate(date));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new TaskTime(startTime));
        return this;
    }
    
    public TaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setEndDate(new TaskDate(date));
        return this;
    }

    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TaskTime(endTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
