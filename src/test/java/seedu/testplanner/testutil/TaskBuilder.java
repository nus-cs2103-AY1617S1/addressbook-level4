package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withAddress(String address) throws IllegalValueException {

        this.person.setEndTime(new EndTime(address));
        return this;
    }

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setDate(new Date1(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.person.setStartTime(new StartTime(email));
        return this;
    }
    
    public TaskBuilder withCompletion(boolean completion) throws IllegalValueException {
        return this;
    }
  


    public TestTask build() {
        return this.person;
    }

}
