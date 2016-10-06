package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new Deadline(deadline));
        return this;
    }
    
    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(new Status(status));
        return this;
    }
    
    public TaskBuilder withRecurrence(String recurrence) throws IllegalValueException {
        this.task.setRecurrence(new Recurrence(recurrence));
        return this;
    }

    public TaskBuilder withSchedule(String schedule) throws IllegalValueException {
        this.task.setSchedule(new Schedule(schedule));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
