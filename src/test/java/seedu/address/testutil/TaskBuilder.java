package seedu.address.testutil;

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
    
    public TaskBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setStartDate(date);
        this.task.setType(TaskType.NON_FLOATING);
        return this;
    }
    
    public TaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setEndDate(date);
        this.task.setType(TaskType.NON_FLOATING);
        return this;
    }
    
    public TaskBuilder withType(TaskType type) throws IllegalValueException {
        this.task.setType(type);
        return this;
    }

    public TestTask build() {
        Task copyTask = new Task(this.task);
        return this.task;
    }

}
