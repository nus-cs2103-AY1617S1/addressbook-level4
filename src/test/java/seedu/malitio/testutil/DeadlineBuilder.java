package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.*;

/**
 *Builds a deadline
 */
public class DeadlineBuilder {

    private TestDeadline task;

    public DeadlineBuilder() {
        this.task = new TestDeadline();
    }

    public DeadlineBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public DeadlineBuilder dueOn(String due) throws IllegalValueException {
        this.task.setDue(new DateTime(due));
        return this;
    }
    
    public DeadlineBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestDeadline build() {
        return this.task;
    }

}
