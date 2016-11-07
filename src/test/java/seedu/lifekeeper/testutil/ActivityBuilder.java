package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.Name;
import seedu.lifekeeper.model.activity.Reminder;
import seedu.lifekeeper.model.tag.Tag;

/**
 * Used to constitute sample activities/tasks/events for testing purposes.
 */
public class ActivityBuilder {

    private TestActivity activity;

    public ActivityBuilder() throws IllegalValueException {
        this.activity = new TestActivity();
    }

    public ActivityBuilder withName(String name) throws IllegalValueException {
        this.activity.setName(new Name(name));
        return this;
    }
    
    public ActivityBuilder withReminder(String address) throws IllegalValueException {
        this.activity.setReminder(new Reminder(address));
        return this;
    }

    public ActivityBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            activity.addTags(new Tag(tag));
        }
        return this;
    }

    public TestActivity build() {
        return this.activity;
    }
}
