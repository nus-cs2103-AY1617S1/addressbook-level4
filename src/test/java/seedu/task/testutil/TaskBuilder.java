package seedu.task.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.task.*;

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
    
    public TaskBuilder withInterval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        this.task.setInterval(new Interval(startDate, startTime, endDate, endTime));
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
