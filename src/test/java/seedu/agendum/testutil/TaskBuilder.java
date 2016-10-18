package seedu.agendum.testutil;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }
    
    /**
     * Copy constructor
     */
    public TaskBuilder(TestTask copy) {
        this.task = new TestTask(copy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withCompletedStatus() {
        this.task.markAsCompleted();
        return this;
    }

    public TaskBuilder withUncompletedStatus() {
        this.task.markAsUncompleted();
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
