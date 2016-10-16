package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;

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

//    public TaskBuilder withAddress(String address) throws IllegalValueException {
//        this.task.setAddress(new Address(address));
//        return this;
//    }
//
//    public TaskBuilder withPhone(String phone) throws IllegalValueException {
//        this.task.setPhone(new Phone(phone));
//        return this;
//    }
//
//    public PersonBuilder withEmail(String email) throws IllegalValueException {
//        this.person.setEmail(new Email(email));
//        return this;
//    }

    public TestTask build() {
        return this.task;
    }

}
