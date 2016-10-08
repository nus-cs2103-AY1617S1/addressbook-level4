package seedu.agendum.testutil;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.tag.Tag;
import seedu.agendum.model.task.*;

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
    
    public TaskBuilder withCompletedStatus() {
        task.markAsCompleted();
        return this;      
    }

    public TaskBuilder withUncompletedStatus() {
        task.markAsUncompleted();
        return this;      
    }

    public TestTask build() {
        return this.task;
    }

}
