package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask Task;

    public TaskBuilder() {
        this.Task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.Task.setName(new Name(name));
        return this;
    }

    public TaskBuilder dueOn(String due) throws IllegalValueException {
        this.Task.setDue(new DateTime(due));
        return this;
    }
    
    public TaskBuilder start(String start) throws IllegalValueException {
        this.Task.setStart(new DateTime(start));
        return this;
    }
    public TaskBuilder end(String end) throws IllegalValueException {
        this.Task.setEnd(new DateTime(end));
        return this;
    }
    
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            Task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.Task;
    }

}
