package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class FloatingTaskBuilder {

    private TestFloatingTest floatingTask;

    public FloatingTaskBuilder() {
        this.floatingTask = new TestFloatingTest();
    }

    public FloatingTaskBuilder withName(String name) throws IllegalValueException {
        this.floatingTask.setName(new Name(name));
        return this;
    }

    public FloatingTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            floatingTask.getTags().add(new Tag(tag));
        }
        return this;
    }

    public FloatingTaskBuilder withAddress(String address) throws IllegalValueException {
        this.floatingTask.setAddress(new Address(address));
        return this;
    }

    public FloatingTaskBuilder withPhone(String phone) throws IllegalValueException {
        this.floatingTask.setPhone(new Phone(phone));
        return this;
    }

    public FloatingTaskBuilder withEmail(String email) throws IllegalValueException {
        this.floatingTask.setEmail(new Email(email));
        return this;
    }

    public TestFloatingTest build() {
        return this.floatingTask;
    }

}
