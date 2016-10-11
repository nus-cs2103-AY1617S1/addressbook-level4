package seedu.manager.testutil;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.tag.Tag;

/**
 *
 */
public class ActivityBuilder {

    private TestActivity activity;

    public ActivityBuilder() {
        this.activity = new TestActivity();
    }

    public ActivityBuilder withName(String name) throws IllegalValueException {
        this.activity.setName(name);
        return this;
    }

    public ActivityBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            activity.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestActivity build() {
        return this.activity;
    }

}
