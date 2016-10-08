package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class FloatingTaskBuilder {

    private TestFloatingTask floatingTask;

    public FloatingTaskBuilder() {
        this.floatingTask = new TestFloatingTask();
    }

    public FloatingTaskBuilder withName(String name) throws IllegalValueException {
        this.floatingTask.setName(new Name(name));
        return this;
    }
    
    public FloatingTaskBuilder withPriority(String priorityValue) throws IllegalValueException {
        this.floatingTask.setPriorityValue(new Priority(priorityValue));
        return this;
    }

    /*
    public FloatingTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            floatingTask.getTags().add(new Tag(tag));
        }
        return this;
    }
    */
    public TestFloatingTask build() {
        return this.floatingTask;
    }

}
