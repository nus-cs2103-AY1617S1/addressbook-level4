package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.*;

/**
 *Builds an event
 */
public class EventBuilder {

    private TestEvent Task;

    public EventBuilder() {
        this.Task = new TestEvent();
    }

    public EventBuilder withName(String name) throws IllegalValueException {
        this.Task.setName(new Name(name));
        return this;
    }
    
    public EventBuilder start(String start) throws IllegalValueException {
        this.Task.setStart(new DateTime(start));
        return this;
    }
    public EventBuilder end(String end) throws IllegalValueException {
        this.Task.setEnd(new DateTime(end));
        return this;
    }
    
    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            Task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestEvent build() {
        return this.Task;
    }

}
