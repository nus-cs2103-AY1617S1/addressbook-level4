package seedu.inbx0.testutil;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.task.*;

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

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new Date(startDate));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new Time(startTime));
        return this;
    }

    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(new Date(endDate));
        return this;
    }
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new Time(endTime));
        return this;
    }
    
    public TaskBuilder withImportance(String level) throws IllegalValueException {
        this.task.setLevel(new Importance(level));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
