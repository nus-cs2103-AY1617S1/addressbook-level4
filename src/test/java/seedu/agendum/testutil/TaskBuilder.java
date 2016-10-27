package seedu.agendum.testutil;

import java.time.LocalDateTime;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;
    private LocalDateTime fixedTime = LocalDateTime.of(2016, 10, 10, 10, 10);

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
        this.task.setLastUpdatedTime(fixedTime);
        return this;
    }
    
    public TaskBuilder withCompletedStatus() {
        this.task.markAsCompleted();
        this.task.setLastUpdatedTime(fixedTime);
        return this;
    }

    public TaskBuilder withUncompletedStatus() {
        this.task.markAsUncompleted();
        this.task.setLastUpdatedTime(fixedTime);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
