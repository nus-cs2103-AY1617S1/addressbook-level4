package seedu.address.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.tag.Tag;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new ActivityName(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withNote(String note) throws IllegalValueException {
        this.person.setNote(new Note(note));
        return this;
    }

    public TaskBuilder withStartDate(String date) throws IllegalValueException {
        this.person.setStartDate(new ActivityDate(date));
        return this;
    }

    public TaskBuilder withStartTime(String time) throws IllegalValueException {
        this.person.setStartTime(new ActivityTime(time));
        return this;
    }
    
    public TaskBuilder withType(String type) throws IllegalValueException {
        this.person.setActivityType(type);
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
