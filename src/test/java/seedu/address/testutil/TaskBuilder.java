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

    public TaskBuilder withDeadline(String date) throws IllegalValueException {
        this.task.setDate(new Deadline(date));
        return this;
    }
    
    public TaskBuilder withEventDate (String startDate, String endDate) throws IllegalValueException {
        this.task.setDate(new EventDate(startDate, endDate));
        return this;
    }
    
    public TaskBuilder withRecurringFrequency(String freq) throws IllegalValueException{
        this.task.setRecurringFrequency(freq);
        return this;
    }


    public TestTask build() {
        return this.task;
    }

}
