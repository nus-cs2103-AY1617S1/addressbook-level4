package jym.manager.testutil;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.tag.Tag;
import jym.manager.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDescription(String name) throws IllegalValueException {
        this.task.setDescription(new Description(name));
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
//    public TaskBuilder withEmail(String email) throws IllegalValueException {
//        this.task.setEmail(new Email(email));
//        return this;
//    }

    public TestTask build() {
        return this.task;
    }

}
