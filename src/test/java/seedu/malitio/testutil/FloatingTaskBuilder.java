package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.*;

/**
 *Builds a floating task
 */
public class FloatingTaskBuilder {

    private TestFloatingTask Task;

    public FloatingTaskBuilder() {
        this.Task = new TestFloatingTask();
    }

    public FloatingTaskBuilder withName(String name) throws IllegalValueException {
        this.Task.setName(new Name(name));
        return this;
    }
    
    public FloatingTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            Task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestFloatingTask build() {
        return this.Task;
    }

}
