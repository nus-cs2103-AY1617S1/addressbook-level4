package seedu.flexitrack.testutil;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.tag.UniqueTagList;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;

//TODO: change the whole class 
/**
 *
 */
public class TaskBuilder {

    private TestTask task;
    private Name name;
    private DateTimeInfo dueDate;
    private DateTimeInfo startTime;
    private DateTimeInfo endTime;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        // this.task.setName(new Name(name));
        this.name = new Name(name);
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        for (String tag : tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDueDate(String dueDate) throws IllegalValueException {
        // this.task.setDueDate(new DateTimeInfo(dueDate));
        this.dueDate = new DateTimeInfo(dueDate);
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        // this.task.setStartTime(new DateTimeInfo(startTime));
        this.startTime = new DateTimeInfo(startTime);
        return this;
    }

    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        // this.task.setEndTime(new DateTimeInfo(endTime));
        this.endTime = new DateTimeInfo(endTime);
        return this;
    }

    public TestTask build() {
        return this.task = new TestTask(this.name, this.dueDate, this.startTime, this.endTime, new UniqueTagList());
    }

}
