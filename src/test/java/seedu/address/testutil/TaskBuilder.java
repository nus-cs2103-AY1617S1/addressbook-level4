package seedu.address.testutil;

import java.time.LocalDateTime;

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

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(new Status(status));
        return this;
    }

    public TaskBuilder withTaskType(String taskType) throws IllegalValueException {
        this.task.setTaskType(taskType);
        return this;
    }
    
    public TaskBuilder withStartDate(LocalDateTime startDate) throws IllegalValueException {
        this.task.setStartDate(startDate);
        return this;
    }
    
    public TaskBuilder withEndDate(LocalDateTime endDate) throws IllegalValueException {
        this.task.setEndDate(endDate);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
