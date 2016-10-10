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

//    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
//        for (String tag: tags) {
//            task.getTags().add(new Tag(tag));
//        }
//        return this;
//    }

//    public TaskBuilder withAddress(String address) throws IllegalValueException {
//        this.task.setAddress(new Address(address));
//        return this;
//    }

    public TaskBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(new Date(date));
        return this;
    }

    public TaskBuilder withTime(String time) throws IllegalValueException {
        this.task.setTime(new Time(time));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
