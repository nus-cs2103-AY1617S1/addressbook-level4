package harmony.testutil;

import harmony.commons.exceptions.IllegalValueException;
import harmony.model.tag.Tag;
import harmony.model.task.*;

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

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Time(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Date(email));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
