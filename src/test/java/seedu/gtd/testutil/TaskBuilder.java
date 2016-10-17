package seedu.gtd.testutil;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.task.*;
import seedu.gtd.model.tag.Tag;

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
    
    public TaskBuilder withDueDate(String dueDate) throws IllegalValueException {
        this.task.setDueDate(new DueDate(dueDate));
        return this;
    }

    public TaskBuilder withAddress(String address) throws IllegalValueException {
        this.task.setAddress(new Address(address));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}

